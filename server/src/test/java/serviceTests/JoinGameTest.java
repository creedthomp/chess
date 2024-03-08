package serviceTests;
import dataAccess.*;
import models.GameInformation;
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
        GameDAO gameDAO = new SqlGameDAO();
        AuthDAO authDAO = new SqlAuthDAO();
        GameInformation gameInformation = new GameInformation();
        gameInformation.setGameName("newgame");
        gameInformation.setGameID(1);
        gameDAO.addGame(gameInformation);
        gameDAO.addWhite(1, "bob");
        FinalResponse finalResponse = service.getResponse(request, authDAO.getAuth("bob"));
    }
    @Test
    public void JoinGamePassTest() throws DataAccessException {
            JoinGameService service = new JoinGameService();
            JoinGameRequest joinGameRequest = new JoinGameRequest();
            GameDAO gameDAO = new SqlGameDAO();
            AuthDAO authDAO = new SqlAuthDAO();

            joinGameRequest.setGameID(1);
            joinGameRequest.setPlayerColor("WHITE");


            //FinalResponse finalResponse = service.getResponse(joinGameRequest, authDAO.getAuth("bob"));

            assertEquals("bob", gameDAO.getGame(1).getWhiteName());

    }

    @Test
    public void JoinGameFailTest() throws DataAccessException {
        try {
            JoinGameService service = new JoinGameService();
            JoinGameRequest joinGameRequest = new JoinGameRequest();

            FinalResponse finalResponse = service.getResponse(joinGameRequest, "bob");

            assertNotNull(finalResponse.getMessage());
        }
        catch(DataAccessException e) {
            assertNotNull(e.getMessage());
            assertEquals("Error: unauthorized", e.getMessage());
        }
    }
}
