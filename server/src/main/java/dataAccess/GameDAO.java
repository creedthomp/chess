package dataAccess;

import models.GameInformation;

import java.util.HashSet;

public interface GameDAO {
    public default void addGame(GameInformation game){}

    public static int getID() {
        return 1;
    }

    public static void setID(int ID) {
    }

    public default HashSet<GameInformation> getGameList() {
        return null;
    }

    public default GameInformation getGame(int gameID) throws DataAccessException {
        return null;
    }

    default void clear() {}

}
