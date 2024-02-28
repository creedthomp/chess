package requests;

import java.util.Objects;

public class JoinGameRequest {
    public String playerColor;

    public int gameID;

    public JoinGameRequest() {}



    public void setGameID(int gameID) {
        this.gameID = gameID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinGameRequest that = (JoinGameRequest) o;
        return gameID == that.gameID && Objects.equals(playerColor, that.playerColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerColor, gameID);
    }
}
