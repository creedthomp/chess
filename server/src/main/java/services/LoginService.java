package services;

import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import models.AuthTokenInformation;

public class LoginService {


    public static LoginResponse getResponse(LoginRequest loginRequest) {
        UserDAO dao = new MemoryUserDAO();
        LoginResponse loginResponse = new LoginResponse();

        if (!(dao.loginUser(loginRequest.username, loginRequest.password))) {// does this work without getters and setters?
            loginResponse.setMessage("Error: unauthorized");
            return loginResponse;
        }

        AuthTokenInformation authTokenInformation = new AuthTokenInformation(loginRequest.username);
        loginResponse.setAuthT(authTokenInformation.getAuthToken());
        loginResponse.setUsername(loginRequest.username);

        return loginResponse;
    }
}
