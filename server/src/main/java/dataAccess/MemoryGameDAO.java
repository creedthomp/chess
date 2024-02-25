package dataAccess;

import models.GameInformation;

import java.util.HashSet;

public class MemoryGameDAO implements GameDAO {
    public static HashSet<GameInformation> gameList = new HashSet<>();

    public void addGame(GameInformation game) {
        gameList.add(game);
    }

    public HashSet<GameInformation> getGameList() {
        return gameList;
    }

    public GameInformation getGame(int gameID) throws DataAccessException { // is gameID int
        for (GameInformation game : gameList) {
            if (game.getGameID() == gameID) {
                return game;
            }
        }
        throw new DataAccessException("Game with ID " + gameID + " was not found.");



    }

}