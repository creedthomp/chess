package serviceTests;
import DataAccess.DataAccessException;
import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import requests.RegisterRequest;
import responses.FinalResponse;
import services.ClearService;
import services.CreateGameService;
import services.RegisterService;

import static org.junit.jupiter.api.Assertions.*;
public class CreateGameTest {

    @BeforeEach
    public void register() throws DataAccessException {
        //clear
        ClearService clearService = new ClearService();
        clearService.clearData();
        // register a user
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.email = "bob";
        registerRequest.password = "bob";
        registerRequest.username = "bob";
        RegisterService registerService = new RegisterService();
        registerService.getResponse(registerRequest);
    }
    @Test
    public void CreateGamePassTest() throws DataAccessException {

        CreateGameRequest request = new CreateGameRequest();
        CreateGameService service = new CreateGameService();
        GameDAO gameDAO = new SqlGameDAO();
        AuthDAO authDAO = new SqlAuthDAO();
        gameDAO.clear();
        request.setGameName("bobsGame");
        FinalResponse finalResponse = service.getResponse(request, authDAO.getAuth("bob"));

        assertNotNull(finalResponse.getGameID());

    }

    @Test
    public void CreateGameFailTest() throws DataAccessException {
        CreateGameRequest request = new CreateGameRequest();
        CreateGameService service = new CreateGameService();
        GameDAO gameDAO = new SqlGameDAO();
        AuthDAO authDAO = new SqlAuthDAO();

        request.setGameName("bobsGame");
        FinalResponse finalResponse = service.getResponse(request, "bob");

        assertNotNull(finalResponse.getMessage());
        assertEquals("Error: unauthorized", finalResponse.getMessage());
    }
}
