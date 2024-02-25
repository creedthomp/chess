package services;

import models.GameInformation;

import java.util.Collection;

public class ListGamesResponse {
    Collection<GameInformation> gameList;

    String message;

    ListGamesResponse() {}

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
}
