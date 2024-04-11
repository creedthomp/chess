package dataAccess;

import DataAccess.DataAccessException;
import models.GameInformation;

import java.util.HashSet;

public class MemoryGameDAO implements GameDAO {
    public static HashSet<GameInformation> gameList = new HashSet<>();
    private static int theID = 1;

    public void addGame(GameInformation game) {

        game.setGameID(theID++);

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


        throw new DataAccessException("Error: bad request");
    }


    public void clear() {
        gameList.clear();
        theID = 1;
    }

}
