package services;

import dataAccess.*;
import models.AuthTokenInformation;
import models.UserInformation;

public class RegisterService {

    public RegisterService() {
    }

    public RegisterResponse getResponse(RegisterRequest registerRequest) throws DataAccessException {
        UserDAO data = new MemoryUserDAO();
        AuthDAO authData = new MemoryAuthDAO();
        RegisterResponse registerResponse = new RegisterResponse();
        // if one of the specified fields is blank
        if ((registerRequest.username.isEmpty()) || (registerRequest.password.isEmpty()) || (registerRequest.email.isEmpty())) {
            registerResponse.setMessage("Error: bad request");
            return registerResponse;
        }
        UserInformation userInformation = new UserInformation(registerRequest.username, registerRequest.password, registerRequest.email);
        // if the username is in the database
        if (data.findUsername(userInformation)) {
            registerResponse.setMessage("Error: already taken");
            return registerResponse;
        }

        // add user to the userList
        data.createUser(userInformation);
        //create the authtoken
        AuthTokenInformation authTokenInformation = new AuthTokenInformation(registerRequest.username);
        // prep the response
        registerResponse.setUsername(authTokenInformation.getUsername());
        registerResponse.setAuthT(authTokenInformation.getAuthToken());
        // add the token to the authorized tokens list
        authData.addAuth(authTokenInformation.getAuthToken());
        return registerResponse;



        //return registerResponse; // is this the right thing to return??

    }
}
