package serviceTests;
import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import requests.ListGamesRequest;
import requests.RegisterRequest;
import responses.FinalResponse;
import services.ClearService;
import services.CreateGameService;
import services.ListGamesService;
import services.RegisterService;

import static org.junit.jupiter.api.Assertions.*;
public class ListGamesTest {

    @BeforeEach
    public void register() throws DataAccessException {
        //clear
        ClearService clearService = new ClearService();
        clearService.getResponse();
        // register a user
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.email = "bob";
        registerRequest.password = "bob";
        registerRequest.username = "bob";
        RegisterService registerService = new RegisterService();
        registerService.getResponse(registerRequest);

        CreateGameRequest request = new CreateGameRequest();
        CreateGameService service = new CreateGameService();
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();

        request.setGameName("bobsGame");
        FinalResponse finalResponse = service.getResponse(request, authDAO.findAuthT("bob"));
    }
    @Test
    public void ListGamePassTest() throws DataAccessException {
        ListGamesService service = new ListGamesService();
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();

        FinalResponse finalResponse = service.getResponse(authDAO.findAuthT("bob"));

        assertEquals(1, gameDAO.getGameList().size());

    }

    @Test
    public void ListGameFailTest() throws DataAccessException {
        CreateGameRequest request = new CreateGameRequest();
        CreateGameService service = new CreateGameService();
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();

        FinalResponse finalResponse = service.getResponse(request, "bob");

        assertNotNull(finalResponse.getMessage());

    }
}
