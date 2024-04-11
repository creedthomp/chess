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
import services.ListGamesService;
import services.RegisterService;

import static org.junit.jupiter.api.Assertions.*;
public class ListGamesTest {

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

        CreateGameRequest request = new CreateGameRequest();
        CreateGameService service = new CreateGameService();
        GameDAO gameDAO = new SqlGameDAO();
        AuthDAO authDAO = new SqlAuthDAO();

        request.setGameName("bobsGame");
        FinalResponse finalResponse = service.getResponse(request, authDAO.getAuth("bob"));

    }
    @Test
    public void ListGamePassTest() throws DataAccessException {
        try {
            ListGamesService service = new ListGamesService();
            GameDAO gameDAO = new SqlGameDAO();
            AuthDAO authDAO = new SqlAuthDAO();

            FinalResponse finalResponse = service.getResponse(authDAO.getAuth("bob"));

            assertEquals(1, gameDAO.getGameList().size());
        }
        catch (DataAccessException ex) {
            throw new DataAccessException(ex.getMessage());
        }

    }

    @Test
    public void ListGameFailTest() throws DataAccessException {

            CreateGameRequest request = new CreateGameRequest();
            CreateGameService service = new CreateGameService();
            GameDAO gameDAO = new SqlGameDAO();
            AuthDAO authDAO = new SqlAuthDAO();

            FinalResponse finalResponse = service.getResponse(request, "bob");

            assertNotNull(finalResponse.getMessage());
            assertEquals("Error: unauthorized", finalResponse.getMessage());


    }
}
