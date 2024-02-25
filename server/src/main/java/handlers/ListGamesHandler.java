package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import services.*;
import spark.Request;
import spark.Response;

public class ListGamesHandler {

    public String handleThisRequest(Request request, Response response) throws DataAccessException {
        ListGamesService listGamesService = new ListGamesService();

        ListGamesRequest listGamesRequest = toGson(request);


        ListGamesResponse listGamesResponse = listGamesService.getResponse(listGamesRequest);

        if (listGamesResponse.getMessage() == null) {
            response.status(200);
        }

        else if (listGamesResponse.getMessage().equals("Error: unauthorized")) {
            response.status(401);
        }

        else{
            response.status(500);
        }

        return fromGson(listGamesResponse);
    }


    private String fromGson(ListGamesResponse response) {
        Gson gson = new Gson();
        return gson.toJson(response);
    }
    private ListGamesRequest toGson(Request request) {
        Gson gson = new Gson();
        return gson.fromJson(request.headers("authorization"), ListGamesRequest.class);
    }
}
