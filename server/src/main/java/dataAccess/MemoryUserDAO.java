package dataAccess;

import DataAccess.DataAccessException;
import models.UserInformation;

import java.util.HashSet;


public class MemoryUserDAO implements UserDAO{
    static public HashSet<UserInformation> userList = new HashSet<>();

    public MemoryUserDAO() {}

    @Override
    public void createUser(UserInformation user){
        userList.add(user);
    }


    @Override
    public boolean findUsername(UserInformation user){
        // if the user is already in the db
        for (UserInformation userinfo : userList) {
            if (userinfo.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean loginUser(String username, String password) throws DataAccessException {
        // if the password and username are correct
        for (UserInformation userinfo : userList) {
            if ((userinfo.getUsername().equals(username)) && userinfo.getPassword().equals(password)) { // do I need to return different errors if different things happen??
                return true;
            }
        }
        throw new DataAccessException("Error: unauthorized");
    }

    public void clear() {
        userList.clear();
    }
}
