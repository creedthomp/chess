package clientTests;

import dataAccess.SqlGameDAO;
import org.junit.jupiter.api.*;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.FinalResponse;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private FinalResponse responsInsertion;
    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
        //Client client = new Client(serverUrl);
        //client.notLoggedInMenu();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void before() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.password = "bob";
        registerRequest.username = "bob";
        registerRequest.email = "bob";
        FinalResponse finalResponse = facade.register(registerRequest);
        responsInsertion = finalResponse;
    }
    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerGood() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.password = "bob";
        registerRequest.username = "bob";
        registerRequest.email = "bob";
        FinalResponse finalResponse = facade.register(registerRequest);
        assertNotNull(finalResponse.getAuthToken())
    }

    @Test
    public void registerBad() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.password = "bobbb";
        registerRequest.username = "";
        registerRequest.email = "";
        FinalResponse finalResponse = facade.register(registerRequest);
        assertNotNull(finalResponse.getMessage())
    }

    @Test
    public void loginGood() {
        LoginRequest loginRequest =new LoginRequest();
        loginRequest.setUsername("bob");
        loginRequest.setPassword("bob");
        FinalResponse finalResponse = facade.login(registerRequest);
        assertTrue(finalResponse.getAuthToken().length() > 2)
    }

    @Test
    public void loginBad() {
        LoginRequest loginRequest =new LoginRequest();
        loginRequest.setUsername("bob");
        loginRequest.setPassword("notmypass");
        FinalResponse finalResponse = facade.login(registerRequest);
        assertNotNull(finalResponse.getMessage())
    }

    @Test
    public void logoutGood() {
        FinalResponse finalResponse = facade.logout(responsInsertion);
        assertNull(finalResponse.getMessage());
        assertNull(finalResponse.getUsername());
    }

    @Test
    public void logoutBad() {
        FinalResponse finalResponse=facade.logout("notanauth");
        assertNotNull(finalResponse.getMessage());
    }

    @Test
    public void createGameGood(){
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName("MYGAME");
        FinalResponse finalResponse = facade.createGame(createGameRequest, responsInsertion);
        assertTrue(finalResponse.getGameID() > 0);

    }

    @Test
    public void createGameBad(){
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName("MYGAME");
        FinalResponse finalResponse = facade.createGame(createGameRequest, responsInsertion);

        CreateGameRequest createGameRequest2 = new CreateGameRequest();
        createGameRequest2.setGameName("MYGAME");
        FinalResponse finalResponse2 = facade.createGame(createGameRequest, responsInsertion);
        assertNotNull(finalResponse2.getMessage());
    }

    @Test
    public void listGamesGood() {
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName("MYGAME");
        FinalResponse finalResponse = facade.createGame(createGameRequest, responsInsertion);
        FinalResponse response = ListGames(responsInsertion);
        assertNotNull(response.getGameList());
    }

    @Test
    public void listGamesBad() {
        FinalResponse response = ListGames(responsInsertion);
        assertNull(response.getGameList());
    }

    @Test
    public void joinGamesGood() {
        SqlGameDAO dao = new SqlGameDAO();
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName("MYGAME");
        FinalResponse response = facade.createGame(createGameRequest, responsInsertion);
        JoinGameRequest joinGameRequest = new JoinGameRequest();
        joinGameRequest.setGameID(response.getGameID());
        joinGameRequest.setPlayerColor("WHITE");
        FinalResponse finalResponse = joinGame(joinGameRequest, responsInsertion);
        assertEquals("bob", dao.getGame(response.getGameID()).getWhiteName());
    }

    @Test
    public void joinGamesBad() {
        SqlGameDAO dao = new SqlGameDAO();
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName("MYGAME");
        FinalResponse response = facade.createGame(createGameRequest, responsInsertion);
        JoinGameRequest joinGameRequest = new JoinGameRequest();
        joinGameRequest.setGameID("notanauthtoken");
        joinGameRequest.setPlayerColor("WHITE");
        FinalResponse finalResponse = joinGame(joinGameRequest, responsInsertion);
        assertNotNull(finalResponse.getMessage());
    }

}
