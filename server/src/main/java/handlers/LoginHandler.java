package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import requests.LoginRequest;
import responses.FinalResponse;
import services.*;
import spark.Request;
import spark.Response;

public class LoginHandler extends ParentHandler {
    public String handleThisRequest(Request request, Response response) throws DataAccessException {
        Gson gson = new Gson();
        LoginService loginService = new LoginService();

        LoginRequest loginRequest = gson.fromJson(request.body(), LoginRequest.class);

        FinalResponse finalResponse = loginService.getResponse(loginRequest);

        // set the response status
        setStatus(finalResponse.getMessage(), response);

        return toJsonExcludingZeroGameID(finalResponse);

    }

}
