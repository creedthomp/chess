package services;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import models.AuthTokenInformation;

public class LogoutService {

    public LogoutService(){}

    public LogoutResponse getResponse(String authToken) throws DataAccessException {

        MemoryAuthDAO authData = new MemoryAuthDAO();
        LogoutResponse logoutResponse = new LogoutResponse();

        // is the authToken authorized?
        if ((authData.findAuth(authToken))) {
            authData.removeAuth(authToken);
        }

        else {
            logoutResponse.setMessage("Error: unauthorized");
        }

        return logoutResponse;

    }

}