package server.WebSocket;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.SqlAuthDAO;
import dataAccess.SqlGameDAO;
import models.GameInformation;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import server.WebSocket.ConnectionManager;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;
import webSocketMessages.serverMessages.ServerMessage.ServerMessageType;

import java.io.IOException;
import java.util.Objects;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

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
        ChessGame game = action.getGame();
        switch (action.getCommandType()) {
            case LEAVE -> leave(authToken, session, gameID);
            case RESIGN -> resign(authToken, session, gameID);
            case JOIN_OBSERVER -> joinObserver(authToken, session, gameID);
            case JOIN_PLAYER -> joinPlayer(authToken, session, gameID, color);
            case MAKE_MOVE -> makeMove(authToken, session, gameID, move, color, game);
        }
    }

    private void leave(String authtoken, Session session, int gameID) throws DataAccessException, IOException {
        String username = authDAO.getUsername(authtoken);
        var message = String.format("%s left the game", username);
        sendNotificationToSome(username, message);
    }

    private void resign(String authToken, Session session, int gameID) throws DataAccessException, IOException {
        String username = authDAO.getUsername(authToken);
        var message = String.format("%s resigned from the game", username);
        sendNotificationToSome(username, message);
    }

    private void joinPlayer(String authtoken, Session session, int gameID, ChessGame.TeamColor color) throws DataAccessException, IOException {
        String username = authDAO.getUsername(authtoken);
        GameInformation gameInformation = gameDAO.getGame(gameID);
        ChessGame game = gameInformation.getGame();
        connections.add(username, session, gameID);
        var message = String.format("%s joined the game as %s", username, color);
        // this might just be a normal notification
        sendGameMessage(username, game);
        sendNotificationToAll(username, message);
    }

    private void joinObserver(String authToken, Session session, int gameID) throws DataAccessException, IOException {
        String username = authDAO.getUsername(authToken);
        GameInformation gameInformation = gameDAO.getGame(gameID);
        ChessGame game = gameInformation.getGame();
        connections.add(username, session, gameID);
        var message = String.format("%s is observing the game", username);
        // am I supposed to send the game after joining the observe?
        sendNotificationToSome(username, message);
    }

    private void makeMove(String authToken, Session session, int gameID, ChessMove move, ChessGame.TeamColor color, ChessGame game) throws DataAccessException, IOException {
        String username = authDAO.getUsername(authToken);
        // I need to check for the validity of the move in the UI
        gameDAO.updateGame(game, gameID);
        var message = String.format("%s made a move", username);
        if (Objects.equals(color, BLACK)) {
            if (game.isInStalemate(WHITE) || game.isInCheckmate(WHITE)) {
                var endMessage = String.format("%s won the game", username);
                sendNotificationToAll(username, endMessage);
            }
        }
        else {
            if (game.isInStalemate(BLACK) || game.isInCheckmate(BLACK)) {
                var endMessage = String.format("%s won the game", username);
                sendNotificationToAll(username, endMessage);
            }
        }
        sendGameMessage(username, game);
        sendNotificationToSome(username, message);
    }

    public void sendGameMessage(String username, ChessGame game) throws IOException {
        Gson gson = new Gson();
        String gameJson = gson.toJson(game);
        ServerMessage serverMessage = new ServerMessage(ServerMessageType.LOAD_GAME, null,null, gameJson );
        connections.broadcast(username, serverMessage);
    }

    public void sendNotificationToAll(String username, String message) throws IOException {
        ServerMessage serverMessage = new ServerMessage(ServerMessageType.NOTIFICATION, message,null, null);
        connections.broadcastAll(username, serverMessage);
    }

    public void sendNotificationToSome(String username, String message) throws IOException {
        ServerMessage serverMessage = new ServerMessage(ServerMessageType.NOTIFICATION, message,null, null);
        connections.broadcast(username, serverMessage);
    }
}
