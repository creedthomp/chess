package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler {
    public String handleThisRequest(Request request, Response response) throws DataAccessException {
        LoginService loginservice = new LoginService();

        LoginRequest loginRequest = toGson(request);

        LoginResponse loginResponse = LoginService.getResponse(loginRequest);

            if (loginResponse.getMessage() != null) {
                if (loginResponse.getMessage().equals("Error: unauthorized")) {
                    response.status(401);
                }
                else{
                    response.status(500);
                }
            }
            else{
                response.status(200);
            }

        return fromGson(loginResponse);

    }
    private String fromGson(LoginResponse response) {
        Gson gson = new Gson();
        return gson.toJson(response);
    }
    private LoginRequest toGson(Request request) {
        Gson gson = new Gson();
        return gson.fromJson(request.body(), LoginRequest.class);
    }
}
