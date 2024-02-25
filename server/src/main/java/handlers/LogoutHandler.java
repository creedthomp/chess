package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import services.*;
import spark.Request;
import spark.Response;

public class LogoutHandler {

    public String handleThisRequest(Request request, Response response) throws DataAccessException {
        LogoutService logoutService = new LogoutService();

        //LogoutRequest logoutRequest = toGson(request);

        LogoutResponse logoutResponse = logoutService.getResponse(request.headers("authorization"));

        if (logoutResponse.getMessage() == null) {
            response.status(200);
        }
        else if (logoutResponse.getMessage().equals("Error: unauthorized")) {
            response.status(401);
        }
        else{
            response.status(500);
        }

        return fromGson(logoutResponse);
    }


    private String fromGson(LogoutResponse response) {
        Gson gson = new Gson();
        return gson.toJson(response);
    }
    private LogoutRequest toGson(Request request) {
        Gson gson = new Gson();
        return gson.fromJson(request.headers("Authorization"), LogoutRequest.class);
    }
}
