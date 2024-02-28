//package serviceTests;
//
//import dataAccess.AuthDAO;
//import dataAccess.DataAccessException;
//import dataAccess.MemoryAuthDAO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import requests.RegisterRequest;
//import responses.FinalResponse;
//import services.ClearService;
//import services.LogoutService;
//import services.RegisterService;
//import static org.junit.jupiter.api.Assertions.*;
//public class LogoutServiceTest {
//
//    @BeforeEach
//    public void register() throws DataAccessException {
//        //clear
//        ClearService clearService = new ClearService();
//        clearService.getResponse();
//        // register a user
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.email = "bob";
//        registerRequest.password = "bob";
//        registerRequest.username = "bob";
//        RegisterService registerService = new RegisterService();
//        registerService.getResponse(registerRequest);
//    }
//
//    @Test
//    public void logoutPassTest() throws DataAccessException {
//        AuthDAO authDAO = new MemoryAuthDAO();
//        LogoutService logoutService = new LogoutService();
//
//        FinalResponse finalResponse = logoutService.getResponse(authDAO.findAuthT("bob"));
//
//        assertNull(finalResponse.getMessage());
//    }
//
//    @Test
//    public void logoutFailTest() throws DataAccessException {
//        AuthDAO authDAO = new MemoryAuthDAO();
//        LogoutService logoutService = new LogoutService();
//        try {
//
//            FinalResponse finalResponse = logoutService.getResponse(authDAO.findAuthT("notanauthtoken"));
//
//            assertEquals("Error: unauthorized", finalResponse.getMessage());
//        }
//        catch (DataAccessException e) {
//            assertEquals("Error: unauthorized", e.getMessage());
//        }
//    }
//
//}
