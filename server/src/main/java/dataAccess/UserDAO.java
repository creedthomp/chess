package dataAccess;

import models.UserInformation;

public interface UserDAO {



    public default void createUser(UserInformation user) throws DataAccessException {

    }


    public default boolean findUsername(UserInformation user) throws DataAccessException {
        return false;
    }

    public default boolean loginUser(String username, String password)throws DataAccessException {
        return false;
    }

    default void clear() throws DataAccessException {}


}
