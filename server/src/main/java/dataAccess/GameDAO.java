package dataAccess;

import models.GameInformation;

import java.util.HashSet;

public interface GameDAO {
    public default void addGame(GameInformation game) throws DataAccessException {}



    public default HashSet<GameInformation> getGameList() throws DataAccessException {
        return null;
    }

    public default GameInformation getGame(int gameID) throws DataAccessException {
        return null;
    }

    default void clear() throws DataAccessException {}

    public default void addBlack(int gameID, String username) throws DataAccessException{

    }
    public default void addWhite(int gameID, String username) throws DataAccessException {

    }

}
