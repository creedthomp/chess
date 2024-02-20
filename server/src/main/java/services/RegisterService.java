package services;

import dataAccess.UserDAO;

public class RegisterService {

    RegisterService() {}

    public RegisterResponse getResponse(RegisterRequest registerRequest) {
        UserDAO data = new UserDAO();
        if (registerRequest.getUsername())
    }
}
