package server.WebSocket;

import chess.*;

import com.google.gson.Gson;
import DataAccess.DataAccessException;
import dataAccess.SqlAuthDAO;
import dataAccess.SqlGameDAO;
import models.GameInformation;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;

import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;
import webSocketMessages.serverMessages.ServerMessage.ServerMessageType;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;


import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessGame.status.OVER;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private final SqlGameDAO gameDAO = new SqlGameDAO();
    private final SqlAuthDAO authDAO = new SqlAuthDAO();
    public WebSocketHandler() throws DataAccessException {
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        ChessMove move = action.getMove();
        ChessGame.TeamColor color = action.getPlayerColor();
        int gameID = action.getGameID();
        String authToken = action.getAuthToken();

        // if the authtoken is invalid
        if (!authDAO.findAuth(authToken)) {
            ServerMessage serverMessage = new ServerMessage(ServerMessageType.ERROR, null, "Error: Invalid AuthToken", null);
            session.getRemote().sendString(new Gson().toJson(serverMessage));
        }
        else {

            switch (action.getCommandType()) {
                case LEAVE -> leave(authToken, gameID);
                case RESIGN -> resign(authToken, session, gameID);
                case JOIN_OBSERVER -> joinObserver(authToken, session, gameID);
                case JOIN_PLAYER -> joinPlayer(authToken, session, gameID, color);
                case MAKE_MOVE -> makeMove(authToken, session, gameID, move, color);
            }
        }
    }

    private void leave(String authtoken, int gameID) throws DataAccessException, IOException {
        String username = authDAO.getUsername(authtoken);
        var message = String.format("%s left the game", username);
        leaveHelp(gameID, username);
        sendNotificationToAll(username, message);
    }

    private void resign(String authToken, Session session, int gameID) throws DataAccessException, IOException {
        try {
            String username = authDAO.getUsername(authToken);
            ChessGame.TeamColor color = getTheColor(username,gameID);
            if (((gameDAO.getGame(gameID).getWhiteName() == null) && (gameDAO.getGame(gameID).getBlackName() == null)) || color == null) {
                throw new DataAccessException("Error: Observer cannot resign");
            }
            if (gameDAO.getGame(gameID).getGame().state == OVER) {
                throw new DataAccessException("Error: Cannot resign, game is over");
            }
            resignError(color, gameID);
            var message = String.format("%s resigned from the game", username);
            ChessGame game = gameDAO.getGame(gameID).getGame();
            game.state = OVER;
            gameDAO.updateGame(game,gameID);
            sendNotificationToAll(username, message);
            sendNotificationToSome(username, message);
        }
        catch(Exception e) {
            sendError(session, e.getMessage());
        }
    }

    private void joinPlayer(String authtoken, Session session, int gameID, ChessGame.TeamColor color) throws DataAccessException, IOException {
        String username = authDAO.getUsername(authtoken);
       try {
           //String username = authDAO.getUsername(authtoken);
           GameInformation gameInformation = gameDAO.getGame(gameID);
           ChessGame game = gameInformation.getGame();
           // check if they are the right team
           correctTeam(username, color, gameID, session);
           connections.add(username, session, gameID);
           var message = String.format("%s joined the game as %s", username, color);
           // this might just be a normal notification
           sendGameMessage(username, game);
           sendNotificationToAll(username, message);
       }
       catch (Exception e) {
           //sendErrormaybethisnotwork(username, "Error: Invalid Game ID");
           sendError(session, e.getMessage());
       }
    }

    private void joinObserver(String authToken, Session session, int gameID) throws DataAccessException, IOException {
        String username = authDAO.getUsername(authToken);
        try {
            GameInformation gameInformation = gameDAO.getGame(gameID);
            ChessGame game = gameInformation.getGame();
            connections.add(username, session, gameID);
            var message = String.format("%s is observing the game", username);
            // am I supposed to send the game after joining the observe?
            sendGameMessage(username, game);
            sendNotificationToAll(username, message);
        }
        catch (Exception e) {
            sendError(session, e.getMessage());
        }
    }

    private void makeMove(String authToken, Session session, int gameID, ChessMove move, ChessGame.TeamColor color) throws DataAccessException, IOException {

        String username = authDAO.getUsername(authToken);
        // I need to check for the validity of the move in the UI
        try {
            ChessGame game = gameDAO.getGame(gameID).getGame();
            var message = String.format("%s made a move", username);
            ChessGame.TeamColor ccolor = getTheColor(username, gameID);
            makeMoveError(session, gameID, move, ccolor, game);
            resignError(color, gameID);
            validateMove(move, game);
            checkMate(game, ccolor);

            // success, made it though error checks
            game.makeMove(move);
            // update the game in the DB
            gameDAO.updateGame(game , gameID);
            sendGameMessage(username, game);
            sendGameMessageAll(username, game);
            sendNotificationToAll(username, message);
//            sendNotificationToSome(username, message);
        }
        catch (Exception e) {
            sendError(session, e.getMessage());
        }
    }

    public void sendGameMessage(String username, ChessGame game) throws IOException {
        Gson gson = new Gson();
        String gameJson = gson.toJson(game);
        ServerMessage serverMessage = new ServerMessage(ServerMessageType.LOAD_GAME, null,null, gameJson );
        connections.broadcast(username, serverMessage);
    }

    public void sendGameMessageAll(String username, ChessGame game) throws IOException {
        Gson gson = new Gson();
        String gameJson = gson.toJson(game);
        ServerMessage serverMessage = new ServerMessage(ServerMessageType.LOAD_GAME, null,null, gameJson );
        connections.broadcastAll(username, serverMessage);
    }

    public void sendNotificationToAll(String username, String message) throws IOException {

        ServerMessage serverMessage = new ServerMessage(ServerMessageType.NOTIFICATION, message,null, null);
        connections.broadcastAll(username, serverMessage);
    }

    public void sendNotificationToSome(String username, String message) throws IOException {
        ServerMessage serverMessage = new ServerMessage(ServerMessageType.NOTIFICATION, message,null, null);
        connections.broadcast(username, serverMessage);
    }

    public void sendError(Session session, String message) throws IOException {
        ServerMessage serverMessage = new ServerMessage(ServerMessageType.ERROR, null, message, null);
        session.getRemote().sendString(new Gson().toJson(serverMessage));
    }

    public void correctTeam(String username, ChessGame.TeamColor color, int gameID, Session session) throws DataAccessException, IOException {
        if (Objects.equals(username, gameDAO.getGame(gameID).getWhiteName()) && (Objects.equals(color, BLACK))) {
            //sendError(session, "Error: join player wrong team");
            throw new DataAccessException("Error: join player wrong team");
        }
        if (Objects.equals(username, gameDAO.getGame(gameID).getBlackName()) && (Objects.equals(color, WHITE))) {
            //sendError(session, "Error: join player wrong team");
            throw new DataAccessException("Error: join player wrong team");
        }
        // join empty team
        if ((gameDAO.getGame(gameID).getBlackName() == null) && (Objects.equals(color, BLACK))) {
            throw new DataAccessException("Error: join empty team");
        }
        if ((gameDAO.getGame(gameID).getWhiteName() == null) && (Objects.equals(color, WHITE))) {
            throw new DataAccessException("Error: join empty team");
        }
    }

    public void makeMoveError(Session session, int gameID, ChessMove move, ChessGame.TeamColor color, ChessGame gsame) throws DataAccessException {
        boolean validity = false;
        ChessGame game = gameDAO.getGame(gameID).getGame();
        //game.validMoves(move.getStartPosition());
        for (ChessMove validmove : game.validMoves(move.getStartPosition())) {
            if (validmove.equals(move)) {
                validity = true;
            }
        }
        if (!validity) {
            throw new DataAccessException("Error: Invalid Move");
        }

        ChessPiece piece =  game.getBoard().getPiece(move.getStartPosition());
        if (!Objects.equals(color, piece.getTeamColor())) {
            throw new DataAccessException("Error: Attempting to move opponents piece");
        }
        if (!game.getTeamTurn().equals(color)) {
            throw new DataAccessException("Error: Not your turn");
        }
        if (color == null) {
            throw new DataAccessException("Error: Observers cannot move pieces");
        }
        if (game.state == OVER) {
            throw new DataAccessException("Error: GAME IS OVER");
        }
        if (game.isInCheckmate(WHITE) || game.isInCheckmate(BLACK) || game.isInStalemate(WHITE) || game.isInStalemate(BLACK)) {
            game.state = OVER;
            throw new DataAccessException("Error: GAME IS OVER");
        }


    }

    public void resignError(ChessGame.TeamColor color,int gameID) throws DataAccessException {
        ChessGame game = gameDAO.getGame(gameID).getGame();
        if (game.state == OVER) {
            throw new DataAccessException("Error: Game is over");
        }
        if ((gameDAO.getGame(gameID).getWhiteName() == null) || (gameDAO.getGame(gameID).getBlackName() == null)) {
            throw new DataAccessException("Error: White player has resigned");
        }

        if ((gameDAO.getGame(gameID).getWhiteName() == null) && (Objects.equals(color, null))) {
            throw new DataAccessException("Error: White player has resigned");
        }

        if ((gameDAO.getGame(gameID).getBlackName() == null) && (Objects.equals(color, null))) {
            throw new DataAccessException("Error: Black player has resigned");
        }




    }

    ChessGame.TeamColor getTheColor(String username, int gameID) throws DataAccessException {
        if ((gameDAO.getGame(gameID).getWhiteName() != null) && (gameDAO.getGame(gameID).getWhiteName().equals(username))) {
            return WHITE;
        }
        if ((gameDAO.getGame(gameID).getBlackName() != null) && (gameDAO.getGame(gameID).getBlackName().equals(username))) {
            return BLACK;
        }
        else {
            return null;
        }
    }

    public boolean checkMate(ChessGame game, ChessGame.TeamColor team) throws DataAccessException{
        if (Objects.equals(team, BLACK)) {
            if (game.isInStalemate(WHITE) || game.isInCheckmate(WHITE)) {
                game.state = OVER;
                throw new DataAccessException("Game is Over due to checkmate or statemate. Move cannot be made");
            }
        }

        else {
            if (game.isInStalemate(BLACK) || game.isInCheckmate(BLACK)) {
                game.state = OVER;
                throw new DataAccessException("Game is Over due to checkmate or statemate. Move cannot be made");
            }
        }
        return true;
    }

    boolean validateMove(ChessMove move, ChessGame game) throws DataAccessException{
        ChessPosition start = move.getStartPosition();
        Collection<ChessMove> valid = game.validMoves(start);
        for (ChessMove chessMove : valid) {
            if (Objects.equals(chessMove, move)) {
                return true;
            }
        }
        throw new DataAccessException("Error Invalid Move");
    }

    public void leaveHelp(int gameID, String username) throws DataAccessException {
        ChessGame.TeamColor color = getTheColor(username, gameID);
        if (color != null) {
            if (Objects.equals(color, BLACK)) {
                gameDAO.addBlack(gameID, null);
            }
            if (Objects.equals(color, WHITE)) {
                gameDAO.addWhite(gameID, null);
            }
        }
    }

}
