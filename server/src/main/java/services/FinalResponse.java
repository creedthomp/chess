package services;

import models.GameInformation;

import java.util.Collection;

public class FinalResponse {

    Collection<GameInformation> gameList;
    String message;
    String username;

    int gameID;
    String authT;

    FinalResponse() {}

    public Collection<GameInformation> getGameList() {
        return gameList;
    }

    public void setGameList(Collection<GameInformation> gameList) {
        this.gameList = gameList;
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

    public String getAuthT() {
        return authT;
    }

    public void setAuthT(String authT) {
        this.authT = authT;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
