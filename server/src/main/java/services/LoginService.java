package services;

import dataAccess.*;
import models.AuthTokenInformation;

public class LoginService {

    public LoginService() {}


    public FinalResponse getResponse(LoginRequest loginRequest) throws DataAccessException {
        UserDAO dao = new MemoryUserDAO();
        AuthDAO authData = new MemoryAuthDAO();
        FinalResponse finalResponse = new FinalResponse();
        try {
            if (!(dao.loginUser(loginRequest.getUsername(), loginRequest.getPassword()))) {// does this work without getters and setters?
                finalResponse.setMessage("Error: unauthorized");
                return finalResponse;
            }

            //create authtoken
            AuthTokenInformation authTokenInformation = new AuthTokenInformation(loginRequest.getUsername());
            //create response
            finalResponse.setAuthT(authTokenInformation.getAuthToken());
            finalResponse.setUsername(loginRequest.getUsername());
            // add token
            authData.addAuth(authTokenInformation);

            return finalResponse;
        }
        catch (DataAccessException exception) {
            finalResponse.setMessage(exception.getMessage());
            return finalResponse;
        }
    }
}
