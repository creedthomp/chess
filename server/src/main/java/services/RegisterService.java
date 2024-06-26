package services;

import DataAccess.DataAccessException;
import dataAccess.*;
import models.AuthTokenInformation;
import models.UserInformation;
import requests.RegisterRequest;
import responses.FinalResponse;

public class RegisterService {

    public RegisterService() {
    }

    public FinalResponse getResponse(RegisterRequest registerRequest) throws DataAccessException {
        UserDAO data = new SqlUserDAO();
        AuthDAO authData = new SqlAuthDAO();
        FinalResponse response = new FinalResponse();
        try {
            // if one of the specified fields is blank
            if ((registerRequest.username == null) || (registerRequest.password == null) || (registerRequest.email == null)) {
                response.setMessage("Error: bad request");
                return response;
            }
            UserInformation userInformation = new UserInformation(registerRequest.username, registerRequest.password, registerRequest.email);
            // if the username is in the database
            if (data.findUsername(userInformation)) {
                response.setMessage("Error: already taken");
                return response;
            }

            // add user to the userList
            data.createUser(userInformation);
            //create the authtoken
            AuthTokenInformation authTokenInformation = new AuthTokenInformation(registerRequest.username);
            // prep the response
            response.setUsername(authTokenInformation.getUsername());
            response.setAuthT(authTokenInformation.getAuthToken());
            // add the token to the authorized tokens list
            authData.addAuth(authTokenInformation);
            return response;
        }
        catch (DataAccessException exception) {
            response.setMessage(exception.getMessage());
            return response;
        }


        //return registerResponse; // is this the right thing to return??

    }
}
