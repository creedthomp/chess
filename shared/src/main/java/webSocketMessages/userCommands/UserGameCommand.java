package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {
    public UserGameCommand(String authToken, int gameID, CommandType type, ChessGame.TeamColor playerColor, ChessMove move ) {
        this.authToken = authToken;
        this.commandType = type;
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.move = move;
    }

    private String authToken;
    private int gameID;
    private CommandType commandType;
    private ChessGame.TeamColor playerColor;

    private ChessMove move;

    private ChessGame game;


    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    public String getAuthToken() {
        return authToken;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public ChessMove getMove() {
        return move;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand))
            return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthToken(), that.getAuthToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthToken());
    }
}
