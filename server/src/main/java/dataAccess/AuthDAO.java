package dataAccess;

import models.AuthTokenInformation;

import java.util.HashSet;

public interface AuthDAO {


    boolean findAuth(String token)throws DataAccessException;

    void addAuth(AuthTokenInformation token) throws DataAccessException;

    void removeAuth(String token) throws DataAccessException;


    String getUsername(String token) throws DataAccessException;

    default HashSet<AuthTokenInformation> getAuthList() {
        return null;
    }

    String getAuth(String user) throws DataAccessException;

    default void clear() {}
}
