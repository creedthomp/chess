package services;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import models.AuthTokenInformation;

public class LogoutService {

    public LogoutService(){}

    public LogoutResponse getResponse(LogoutRequest logoutRequest) throws DataAccessException {

        MemoryAuthDAO authData = new MemoryAuthDAO();
        LogoutResponse logoutResponse = new LogoutResponse();

        // is the authToken authorized?
        if ((authData.findAuth(logoutRequest.authT))) {
            authData.removeAuth(logoutRequest.authT);
        }

        else {
            logoutResponse.setMessage("Error: unauthorized");
        }

        return logoutResponse;

    }

}