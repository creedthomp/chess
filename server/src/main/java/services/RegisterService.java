package services;

import dataAccess.DataAccessException;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import models.AuthTokenInformation;
import models.UserInformation;

public class RegisterService {

    public RegisterService() {
    }

    public RegisterResponse getResponse(RegisterRequest registerRequest) throws DataAccessException {
        UserDAO data = new MemoryUserDAO();
        RegisterResponse registerResponse = new RegisterResponse();
        // if one of the specified fields is blank
        if ((registerRequest.getUsername().isEmpty()) || (registerRequest.getPassword().isEmpty()) || (registerRequest.getEmail().isEmpty())) {
            registerResponse.setMessage("Error: bad request");
            return registerResponse;
        }
        UserInformation userInformation = new UserInformation(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
        // if the username is in the database
        if (data.findUsername(userInformation)) {
            registerResponse.setMessage("Error: already taken");
            return registerResponse;
        }

        data.createUser(userInformation);
        AuthTokenInformation authTokenInformation = new AuthTokenInformation(registerRequest.getUsername());
        //String token = authTokenInformation.getAuthToken();
        registerResponse.setUsername(authTokenInformation.getUsername());
        registerResponse.setAuthT(authTokenInformation.getAuthToken());
        return registerResponse;



        //return registerResponse; // is this the right thing to return??

    }
}
