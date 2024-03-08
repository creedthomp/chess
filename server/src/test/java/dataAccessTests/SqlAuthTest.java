package dataAccessTests;

import dataAccess.*;
import models.AuthTokenInformation;
import models.UserInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class SqlAuthTest {

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
    public void findAuthPassTest() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        AuthDAO authDAO = new SqlAuthDAO();
        UserInformation user = new UserInformation("bob", "bbobb", "bob@");
        userDAO.createUser(user);
        AuthTokenInformation authTokenInformation = new AuthTokenInformation("bob");
        authDAO.addAuth(authTokenInformation);
        assertTrue(authDAO.findAuth(authTokenInformation.getAuthToken()));
    }

    @Test
    public void findAuthFailTest() throws DataAccessException {
        AuthDAO authDAO = new SqlAuthDAO();
        assertFalse(authDAO.findAuth("notanauthtoken"));
    }

    @Test
    public void getAuthListPassTest() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        AuthDAO authDAO = new SqlAuthDAO();
        UserInformation user = new UserInformation("bob", "bbobb", "bob@");
        userDAO.createUser(user);
        AuthTokenInformation authTokenInformation = new AuthTokenInformation("bob");
        authDAO.addAuth(authTokenInformation);
        assertNotNull(authDAO.getAuthList());
    }

    @Test
    public void getAuthListFailTest() throws DataAccessException {
        AuthDAO authDAO = new SqlAuthDAO();
        assertTrue(authDAO.getAuthList().isEmpty());
    }

    @Test
    public void addAuthPassTest() throws DataAccessException {
        AuthDAO authDAO = new SqlAuthDAO();
        AuthTokenInformation authTokenInformation = new AuthTokenInformation("bob");
        authDAO.addAuth(authTokenInformation);
        assertTrue(authDAO.findAuth(authTokenInformation.getAuthToken()));
    }

    @Test
    public void addAuthFailTest() throws DataAccessException {
        AuthDAO authDAO = new SqlAuthDAO();
        AuthTokenInformation authTokenInformation = new AuthTokenInformation("bob");
        authDAO.addAuth(authTokenInformation);
        assertFalse(authDAO.findAuth("nope"));
    }

    @Test
    public void removeAuthPassTest() throws DataAccessException {
        AuthDAO authDAO = new SqlAuthDAO();
        AuthTokenInformation authTokenInformation = new AuthTokenInformation("bob");
        authDAO.addAuth(authTokenInformation);
        authDAO.removeAuth(authTokenInformation.getAuthToken());
        assertFalse(authDAO.findAuth(authTokenInformation.getAuthToken()));
    }

    @Test
    public void removeAuthFailTest() throws DataAccessException {
        try {
            AuthDAO authDAO = new SqlAuthDAO();
            AuthTokenInformation authTokenInformation = new AuthTokenInformation("bob");
            authDAO.addAuth(authTokenInformation);
            // will this error?
            authDAO.removeAuth("random");
            assertTrue(authDAO.findAuth(authTokenInformation.getAuthToken()));
        }
        catch (DataAccessException e){
            assertEquals("Error: unable to update", e.getMessage());
        }
    }

    @Test
    public void getAuthPassTest() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        AuthDAO authDAO = new SqlAuthDAO();
        UserInformation user = new UserInformation("bob", "bbobb", "bob@");
        userDAO.createUser(user);
        AuthTokenInformation authTokenInformation = new AuthTokenInformation("bob");
        authDAO.addAuth(authTokenInformation);
        assertEquals(authTokenInformation.getAuthToken(),authDAO.getAuth("bob"));
    }

    @Test
    public void getAuthFailTest() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        AuthDAO authDAO = new SqlAuthDAO();
        UserInformation user = new UserInformation("bob", "bbobb", "bob@");
        userDAO.createUser(user);
        AuthTokenInformation authTokenInformation = new AuthTokenInformation("bob");
        authDAO.addAuth(authTokenInformation);
        assertNotEquals("bob",authDAO.getAuth("bob"));
    }

    @Test
    public void getUsernamePassTest() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        AuthDAO authDAO = new SqlAuthDAO();
        UserInformation user = new UserInformation("bob", "bbobb", "bob@");
        userDAO.createUser(user);
        AuthTokenInformation authTokenInformation = new AuthTokenInformation("bob");
        authDAO.addAuth(authTokenInformation);
        String correct = authDAO.getUsername(authTokenInformation.getAuthToken());
        assertEquals("bob", correct);
    }

    @Test
    public void getUsernameFailTest() throws DataAccessException {
        UserDAO userDAO = new SqlUserDAO();
        AuthDAO authDAO = new SqlAuthDAO();
        UserInformation user = new UserInformation("bob", "bbobb", "bob@");
        userDAO.createUser(user);
        AuthTokenInformation authTokenInformation = new AuthTokenInformation("bob");
        authDAO.addAuth(authTokenInformation);
        assertNotEquals("blob",authDAO.getAuth(authTokenInformation.getAuthToken()));
    }

    @Test
    public void clear() throws DataAccessException {
        AuthDAO authDAO = new SqlAuthDAO();
        AuthTokenInformation authTokenInformation = new AuthTokenInformation("bob");
        authDAO.addAuth(authTokenInformation);
        authDAO.clear();
        assertFalse(authDAO.findAuth(authTokenInformation.getAuthToken()));
    }

}
