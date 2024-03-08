package serviceTests;

import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import services.ClearService;
import services.RegisterService;
import static org.junit.jupiter.api.Assertions.*;
public class ClearTest {


    @BeforeEach
    public void register() throws DataAccessException {
        // register a user
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.email = "bob";
        registerRequest.password = "bob";
        registerRequest.username = "bob";
        RegisterService registerService = new RegisterService();
        registerService.getResponse(registerRequest);
    }

    @Test
    public void clearPassTest() throws DataAccessException {
         new ClearService().clearData();
        AuthDAO authDAO = new SqlAuthDAO();
        // compare to an empty list
        assertTrue(authDAO.getAuthList().isEmpty());
    }

}
