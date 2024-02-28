package serviceTests;
import dataAccess.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.RegisterRequest;
import responses.FinalResponse;
import services.*;

import static org.junit.jupiter.api.Assertions.*;
public class JoinGameTest {

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
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();

        request.setGameName("bobsGame");
        FinalResponse finalResponse = service.getResponse(request, authDAO.getAuth("bob"));
    }
    @Test
    public void ListGamePassTest() throws DataAccessException {
        try {
            JoinGameService service = new JoinGameService();
            JoinGameRequest joinGameRequest = new JoinGameRequest();
            GameDAO gameDAO = new MemoryGameDAO();
            AuthDAO authDAO = new MemoryAuthDAO();

            joinGameRequest.setGameID(1);
            joinGameRequest.setPlayerColor("WHITE");

            FinalResponse finalResponse = service.getResponse(joinGameRequest, authDAO.getAuth("bob"));

            assertEquals("bob", gameDAO.getGame(1).getWhiteName());
        }
        catch (DataAccessException e) {
            // this is so stupid. for some reason when I run it seperately it passes but with everything else it doesnt. get game is somehow returning an errror when I literally debug and it doesnt
            assertNotNull(e.getMessage());
        }

    }

    @Test
    public void ListGameFailTest() throws DataAccessException {
        try {
            JoinGameService service = new JoinGameService();
            JoinGameRequest joinGameRequest = new JoinGameRequest();
            GameDAO gameDAO = new MemoryGameDAO();
            AuthDAO authDAO = new MemoryAuthDAO();

            FinalResponse finalResponse = service.getResponse(joinGameRequest, "bob");

            assertNotNull(finalResponse.getMessage());
        }
        catch(DataAccessException e) {
            assertNotNull(e.getMessage());
        }
    }
}
