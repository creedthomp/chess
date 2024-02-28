package dataAccess;

import models.AuthTokenInformation;
import models.UserInformation;

import java.util.HashSet;

public interface AuthDAO {


    boolean findAuth(String token)throws DataAccessException;

    void addAuth(AuthTokenInformation token) throws DataAccessException;

    void removeAuth(String token) throws DataAccessException;


    String findUsername(String token) throws DataAccessException;

    public default String findAuthT(String user) throws DataAccessException {
        return "string";
    }

//    default boolean findInfo(String username){
//        return false;
//    }
//
//    default void removeUsername(String username) {}
    default HashSet<AuthTokenInformation> getAuthList() {
        return null;
    }
    default void clear() {}
}
