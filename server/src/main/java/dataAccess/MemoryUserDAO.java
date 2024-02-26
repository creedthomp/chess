package dataAccess;

import models.UserInformation;

import java.util.HashSet;

public class MemoryUserDAO implements UserDAO{
    static public HashSet<UserInformation> userList = new HashSet<>();

    public MemoryUserDAO() {}

    @Override
    public void createUser(UserInformation user) throws DataAccessException {
        userList.add(user);
    }

    @Override
    public boolean findUser(UserInformation user) throws DataAccessException {
        if (userList.contains(user)) {
            return true;
        }
        throw new DataAccessException("Error: ");
    }

    @Override
    public boolean findUsername(UserInformation user) throws DataAccessException{
        for (UserInformation userinfo : userList) {
            if (userinfo.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        throw new DataAccessException("Error: user not found");
    }

    @Override
    public boolean loginUser(String username, String password) throws DataAccessException {
        for (UserInformation userinfo : userList) {
            if ((userinfo.getUsername().equals(username)) && userinfo.getPassword().equals(password)) { // do I need to return different errors if different things happen??
                return true;
            }
        }
        throw new DataAccessException("Error: wrong username or password");
    }
}
