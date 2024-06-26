package webSocketMessages.serverMessages;

import chess.ChessGame;

import java.util.Objects;

/**
 * Represents a Message the server can send through a WebSocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class ServerMessage {
    ServerMessageType serverMessageType;
    // chess game or string??
    private String game;
    private String message;
    private String errorMessage;
    public enum ServerMessageType {
        LOAD_GAME,
        ERROR,
        NOTIFICATION
    }

    public ServerMessage(ServerMessageType type, String message, String errorMessage, String game) {
        this.serverMessageType = type;
        this.message = message;
        this.errorMessage = errorMessage;
        this.game = game;
    }





    public String getGame() {
        return game;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return errorMessage;
    }

    public ServerMessageType getServerMessageType() {
        return this.serverMessageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ServerMessage))
            return false;
        ServerMessage that = (ServerMessage) o;
        return getServerMessageType() == that.getServerMessageType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServerMessageType());
    }
}
