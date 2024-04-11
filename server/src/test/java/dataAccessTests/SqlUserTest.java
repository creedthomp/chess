package dataAccessTests;

import DataAccess.DataAccessException;
import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import models.UserInformation;
import static org.junit.jupiter.api.Assertions.*;

public class SqlUserTest {

    @BeforeEach
    public void before() throws DataAccessException {
        GameDAO gameDAO = new SqlGameDAO();
        AuthDAO authDAO = new SqlAuthDAO();
        UserDAO userDAO = new SqlUserDAO();
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }
    @Test
    public void createUserPassTest() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        UserInformation user = new UserInformation("bob", "bbobb", "bob@");
        userDAO.createUser(user);
        assertTrue(userDAO.findUsername(user));
    }

    @Test
    public void createUserFailTest() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        UserInformation user = new UserInformation("b", "bbb", "bob@");
        assertFalse(userDAO.findUsername(user));
    }

    @Test
    public void findUsernamePassTest() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        UserInformation user = new UserInformation("bob", "bbobb", "bob@");
        userDAO.createUser(user);
        assertTrue(userDAO.findUsername(user));
    }

    @Test
    public void findUsernameFailTest() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        UserInformation user = new UserInformation("b", "bbb", "bob@");
        assertFalse(userDAO.findUsername(user));
    }

    @Test
    public void loginUserPassTest() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        UserInformation user = new UserInformation("bob", "bbobb", "bob@");
        userDAO.createUser(user);
        assertTrue(userDAO.loginUser("bob", "bbobb"));
    }

    @Test
    public void loginUserFailTest() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        UserInformation user = new UserInformation("bob", "bbobb", "bob@");
        userDAO.createUser(user);
        assertFalse(userDAO.loginUser("bob", "wrongpass"));
    }

    @Test
    public void clear() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        UserInformation user = new UserInformation("bob", "bbobb", "bob@");
        userDAO.createUser(user);
        userDAO.clear();
        assertFalse(userDAO.findUsername(user));
    }


}
