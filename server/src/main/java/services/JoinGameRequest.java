package services;

import java.util.Objects;

public class JoinGameRequest {
    String teamColor;

    int gameID;

    JoinGameRequest() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinGameRequest that = (JoinGameRequest) o;
        return gameID == that.gameID && Objects.equals(teamColor, that.teamColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, gameID);
    }
}
