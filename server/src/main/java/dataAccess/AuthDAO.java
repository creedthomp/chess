package dataAccess;

import models.UserInformation;

public interface AuthDAO {


    boolean findAuth(String token)throws DataAccessException;

    void addAuth(String token) throws DataAccessException;

    void removeAuth(String token) throws DataAccessException;

    String printAuth() throws DataAccessException;
}
