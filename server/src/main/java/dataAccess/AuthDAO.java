package dataAccess;

import DataAccess.DataAccessException;
import models.AuthTokenInformation;

import java.util.HashSet;

public interface AuthDAO {


    default boolean findAuth(String token) throws DataAccessException {
        return false;
    }

    default void addAuth(AuthTokenInformation token) throws DataAccessException {

    }


    default void removeAuth(String token) throws DataAccessException {

    }

    default String getUsername(String token) throws DataAccessException {
        return null;
    }

    default HashSet<AuthTokenInformation> getAuthList() throws DataAccessException {
        return null;
    }

    public default String getAuth(String user) throws DataAccessException {
        return "string";
    }

    default void clear() throws DataAccessException {}
}
