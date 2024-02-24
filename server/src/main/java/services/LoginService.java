package services;

import dataAccess.*;
import models.AuthTokenInformation;

public class LoginService {

    public LoginService() {}


    public LoginResponse getResponse(LoginRequest loginRequest) throws DataAccessException {
        UserDAO dao = new MemoryUserDAO();
        AuthDAO authData = new MemoryAuthDAO();
        LoginResponse loginResponse = new LoginResponse();

        if (!(dao.loginUser(loginRequest.getUsername(), loginRequest.getPassword()))) {// does this work without getters and setters?
            loginResponse.setMessage("Error: unauthorized");
            return loginResponse;
        }

        //create authtoken
        AuthTokenInformation authTokenInformation = new AuthTokenInformation(loginRequest.getUsername());
        //create response
        loginResponse.setAuthT(authTokenInformation.getAuthToken());
        loginResponse.setUsername(loginRequest.getUsername());
        // add token
        authData.addAuth(authTokenInformation.getAuthToken());

        return loginResponse;
    }
}
