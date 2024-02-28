package serviceTests;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.FinalResponse;
import services.ClearService;
import services.LoginService;
import services.RegisterService;

import static org.junit.jupiter.api.Assertions.*;
public class LoginServiceTest {

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
    public void LoginPassTest() throws DataAccessException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("bob");
        loginRequest.setPassword("bob");

        LoginService loginService = new LoginService();
        FinalResponse finalResponse = loginService.getResponse(loginRequest);

        assertNotNull(finalResponse.getAuthT());
        assertEquals("bob", finalResponse.getUsername());
    }


    @Test
    public void LoginFailTest() throws DataAccessException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("bob");
        loginRequest.setPassword("");

        LoginService loginService = new LoginService();
        FinalResponse finalResponse = loginService.getResponse(loginRequest);

        assertNotNull(finalResponse.getMessage());
        assertEquals("Error: unauthorized", finalResponse.getMessage());
    }

}
