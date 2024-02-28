package serviceTests;

import dataAccess.*;
import models.AuthTokenInformation;
import models.UserInformation;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import responses.FinalResponse;
import services.RegisterService;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
public class RegisterServiceTest {
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();
    @Test
    public void RegisterPassTest() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.email = "bob";
        registerRequest.password = "bob";
        registerRequest.username = "bobby";
        RegisterService registerService = new RegisterService();
        FinalResponse finalResponse = registerService.getResponse(registerRequest);

        //assertNull(finalResponse.getMessage());
        assertEquals("bobby", finalResponse.getUsername());
    }


    @Test
    public void RegisterFailTest() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.email = null;
        registerRequest.password = null;
        registerRequest.username = "bobb";
        RegisterService registerService = new RegisterService();
        FinalResponse finalResponse = registerService.getResponse(registerRequest);

        assertNotNull(finalResponse.getMessage());
        //assertEquals("Error: bad request", finalResponse.getMessage());

    }
}
