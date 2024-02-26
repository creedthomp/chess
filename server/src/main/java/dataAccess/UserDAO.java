package dataAccess;

import models.UserInformation;

import java.util.ArrayList;
import java.util.HashSet;

public interface UserDAO {

//    static public HashSet<UserInformation> userList = new HashSet<>();
//    UserDAO() {}


    public default void createUser(UserInformation user) throws DataAccessException {

    }

    public default boolean findUser(UserInformation user)throws DataAccessException {
        return false;
    }

    public default boolean findUsername(UserInformation user) throws DataAccessException {
        return false;
    }

    public default boolean loginUser(String username, String password)throws DataAccessException {
        return false;
    }
}
