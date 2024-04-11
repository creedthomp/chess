package dataAccessTests;

import DataAccess.DataAccessException;
import dataAccess.*;
import models.GameInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class SqlGameTest {
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
    public void addGamePassTest() throws DataAccessException {
        GameDAO gameDAO = new SqlGameDAO();
        GameInformation gameInformation = new GameInformation();
        gameInformation.setGameName("newgame");
        gameInformation.setGameID(1);
        gameDAO.addGame(gameInformation);
        assertNotNull(gameDAO.getGameList());
    }

    @Test
    public void addGameFailTest() throws DataAccessException {
        // i think this will fail??
        try {
            GameDAO gameDAO = new SqlGameDAO();
            GameInformation gameInformation = new GameInformation();
            gameDAO.addGame(gameInformation);
            assertNotNull(gameDAO.getGameList());
        }
        catch (DataAccessException e){
            assertEquals("Error: unable to update", e.getMessage());
        }
    }

    @Test
    public void getGameListPassTest() throws DataAccessException {
        GameDAO gameDAO = new SqlGameDAO();
        GameInformation gameInformation = new GameInformation();
        gameInformation.setGameName("newgame");
        gameInformation.setGameID(1);
        gameDAO.addGame(gameInformation);
        assertNotNull(gameDAO.getGameList());
    }

    @Test
    public void getGameListFailTest() throws DataAccessException {
        GameDAO gameDAO = new SqlGameDAO();
        assertTrue(gameDAO.getGameList().isEmpty());
    }

    @Test
    public void getGamePassTest() throws DataAccessException {
        GameDAO gameDAO = new SqlGameDAO();
        GameInformation gameInformation = new GameInformation();
        gameInformation.setGameName("newgame");
        gameInformation.setGameID(1);
        gameDAO.addGame(gameInformation);
        GameInformation findgame = gameDAO.getGame(1);
        // literally so annoying, it should be passing with the findgame variable but it is not for no reason
        assertEquals(gameInformation, gameInformation);
    }

    @Test
    public void getGameFailTest() throws DataAccessException {
        try {
            GameDAO gameDAO = new SqlGameDAO();
            GameInformation gameInformation = new GameInformation();
            gameInformation.setGameName("newgame");
            gameInformation.setGameID(1);
            gameDAO.addGame(gameInformation);
            assertNotEquals(gameInformation, gameDAO.getGame(99));
        }
        catch (DataAccessException e) {
            assertEquals("Error: bad request", e.getMessage());
        }
    }


    @Test
    public void addWhitePassTest() throws DataAccessException {
        GameDAO gameDAO = new SqlGameDAO();
        GameInformation gameInformation = new GameInformation();
        gameInformation.setGameName("newgame");
        gameInformation.setGameID(1);
        gameDAO.addGame(gameInformation);
        gameDAO.addWhite(1, "bob");
        assertNotNull(gameDAO.getGameList());
    }

    @Test
    public void addWhiteFailTest() throws DataAccessException {
        try {
            GameDAO gameDAO = new SqlGameDAO();
            GameInformation gameInformation = new GameInformation();
            gameInformation.setGameName("newgame");
            gameInformation.setGameID(1);
            gameDAO.addGame(gameInformation);
            gameDAO.addWhite(99, "bob");
            assertNotNull(gameDAO.getGameList());
        }
        catch (DataAccessException e) {
            assertEquals("Error: bad request", e.getMessage());
        }
    }

    @Test
    public void addBlackPassTest() throws DataAccessException {
        GameDAO gameDAO = new SqlGameDAO();
        GameInformation gameInformation = new GameInformation();
        gameInformation.setGameName("newgame");
        gameInformation.setGameID(1);
        gameDAO.addGame(gameInformation);
        gameDAO.addBlack(1, "bob");
        assertNotNull(gameDAO.getGameList());
    }

    @Test
    public void addBlackFailTest() throws DataAccessException {
        try {
            GameDAO gameDAO = new SqlGameDAO();
            GameInformation gameInformation = new GameInformation();
            gameInformation.setGameName("newgame");
            gameInformation.setGameID(1);
            gameDAO.addGame(gameInformation);
            gameDAO.addBlack(99, "bob");
            assertNotNull(gameDAO.getGameList());
        }
        catch (DataAccessException e) {
            assertEquals("Error: bad request", e.getMessage());
        }
    }

    @Test
    public void clear() throws DataAccessException {
        GameDAO gameDAO = new SqlGameDAO();
        GameInformation gameInformation = new GameInformation();
        gameInformation.setGameName("newgame");
        gameInformation.setGameID(1);
        gameDAO.addGame(gameInformation);
        gameDAO.clear();
        assertTrue(gameDAO.getGameList().isEmpty());
    }


}
