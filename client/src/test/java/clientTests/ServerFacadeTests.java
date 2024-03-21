package clientTests;

import dataAccess.DataAccessException;
import dataAccess.SqlGameDAO;
import org.junit.jupiter.api.*;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.FinalResponse;
import server.Server;
import ui.ServerFacade;


import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {
    private static Server server;
    private static FinalResponse responsInsertion;

    private FinalResponse loginResponse;
    static ServerFacade facade;
    @BeforeAll
    public static void init() throws DataAccessException {
        server = new Server();
        //var port = server.run(0);
        //var port = server.run("http://localhost:8080");
        //System.out.println("Started test HTTP server on " + port);
        //ServerFacade facade = new ServerFacade(port);
        facade = new ServerFacade("http://localhost:8080");
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.password = "bob";
//        registerRequest.username = "bob";
//        registerRequest.email = "bob";
//        FinalResponse finalResponse = facade.register(registerRequest);
//        responsInsertion = finalResponse;

    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

//    @BeforeEach
//    public void beforeeach() throws DataAccessException {
//        LoginRequest loginRequest =new LoginRequest();
//        loginRequest.setUsername("bob");
//        loginRequest.setPassword("bob");
//        loginResponse = facade.login(loginRequest);
//    }

//    @BeforeAll
//    public static void before() throws DataAccessException {
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.password = "bob";
//        registerRequest.username = "bob";
//        registerRequest.email = "bob";
//        FinalResponse finalResponse = facade.register(registerRequest);
//        responsInsertion = finalResponse;
//    }
    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerGood() throws DataAccessException {
        try {
            facade.logout(responsInsertion);
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.password = "bob";
            registerRequest.username = "bob";
            registerRequest.email = "bob";
            FinalResponse finalResponse = facade.register(registerRequest);
            assertNotNull(finalResponse.getAuthToken());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void registerBad() throws DataAccessException {
        try {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.password = "bobbb";
            registerRequest.username = "";
            registerRequest.email = "";
            FinalResponse finalResponse = facade.register(registerRequest);
            assertNull(finalResponse.getMessage());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void loginGood() throws DataAccessException {
        try {
            assertTrue(loginResponse.getAuthToken().length() > 2);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void loginBad() throws DataAccessException {

        try {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername("bob");
            loginRequest.setPassword("notmypass");
            FinalResponse finalResponse = facade.login(loginRequest);
            assertNotNull(finalResponse.getMessage());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void logoutGood() throws DataAccessException {
        try {
            FinalResponse finalResponse = facade.logout(responsInsertion);
            assertNull(finalResponse.getMessage());
            assertNull(finalResponse.getUsername());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void logoutBad() throws DataAccessException {
        try {
            FinalResponse fResponse = new FinalResponse();
            fResponse.setAuthT("notauth");
            FinalResponse finalResponse = facade.logout(fResponse);
            assertNotNull(finalResponse.getMessage());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void createGameGood() throws DataAccessException {
        try {
            CreateGameRequest createGameRequest = new CreateGameRequest();
            createGameRequest.setGameName("MYGAME");
            FinalResponse finalResponse = facade.createGame(createGameRequest, responsInsertion);
            assertTrue(finalResponse.getGameID() > 0);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Test
    public void createGameBad() throws DataAccessException {
        try {
            CreateGameRequest createGameRequest = new CreateGameRequest();
            createGameRequest.setGameName("MYGAME");
            FinalResponse finalResponse = facade.createGame(createGameRequest, responsInsertion);

            CreateGameRequest createGameRequest2 = new CreateGameRequest();
            createGameRequest2.setGameName("MYGAME");
            FinalResponse finalResponse2 = facade.createGame(createGameRequest, responsInsertion);
            assertNotNull(finalResponse2.getMessage());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void listGamesGood() throws DataAccessException {
        try {
            CreateGameRequest createGameRequest = new CreateGameRequest();
            createGameRequest.setGameName("MYGAME");
            FinalResponse finalResponse = facade.createGame(createGameRequest, responsInsertion);
            FinalResponse response = facade.ListGames(responsInsertion);
            assertNotNull(response.getGameList());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void listGamesBad() throws DataAccessException {
        try {
            FinalResponse response = facade.ListGames(responsInsertion);
            assertNull(response.getGameList());
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void joinGamesGood() throws DataAccessException {
        try {
            SqlGameDAO dao = new SqlGameDAO();
            CreateGameRequest createGameRequest = new CreateGameRequest();
            createGameRequest.setGameName("MYGAME");
            FinalResponse response = facade.createGame(createGameRequest, responsInsertion);
            JoinGameRequest joinGameRequest = new JoinGameRequest();
            joinGameRequest.setGameID(response.getGameID());
            joinGameRequest.setPlayerColor("WHITE");
            FinalResponse finalResponse = facade.joinGame(joinGameRequest, responsInsertion);
            assertEquals("bob", dao.getGame(response.getGameID()).getWhiteName());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void joinGamesBad() throws DataAccessException {
        try {
            CreateGameRequest createGameRequest = new CreateGameRequest();
            createGameRequest.setGameName("MYGAME");
            facade.createGame(createGameRequest, responsInsertion);
            JoinGameRequest joinGameRequest = new JoinGameRequest();
            joinGameRequest.setGameID(1232131223);
            joinGameRequest.setPlayerColor("WHITE");
            FinalResponse finalResponse = facade.joinGame(joinGameRequest, responsInsertion);
            assertNotNull(finalResponse.getMessage());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
