package services;

import DataAccess.DataAccessException;
import dataAccess.*;
import models.AuthTokenInformation;
import requests.LoginRequest;
import responses.FinalResponse;

public class LoginService {

    public LoginService() {}


    public FinalResponse getResponse(LoginRequest loginRequest) throws DataAccessException {
        UserDAO dao = new SqlUserDAO();
        AuthDAO authData = new SqlAuthDAO();
        FinalResponse finalResponse = new FinalResponse();
        try {
            if (!(dao.loginUser(loginRequest.getUsername(), loginRequest.getPassword()))) {
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
