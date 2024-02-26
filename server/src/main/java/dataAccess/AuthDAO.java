package dataAccess;

import models.AuthTokenInformation;
import models.UserInformation;

public interface AuthDAO {


    boolean findAuth(String token)throws DataAccessException;

    void addAuth(AuthTokenInformation token) throws DataAccessException;

    void removeAuth(String token) throws DataAccessException;

    String printAuth() throws DataAccessException;

    String findUsername(String token) throws DataAccessException;

    default void clear() {}
}
