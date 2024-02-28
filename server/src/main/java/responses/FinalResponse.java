package responses;

import models.GameInformation;

import java.util.Collection;

public class FinalResponse {

    Collection<GameInformation> games;
    String message;
    String username;

    int gameID = 0;
    String authToken;

    public FinalResponse() {}


    public void setGameList(Collection<GameInformation> gameList) {
        this.games = gameList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setAuthT(String authT) {
        this.authToken = authT;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
