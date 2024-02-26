package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import services.*;
import spark.Request;
import spark.Response;

public class ListGamesHandler extends ParentHandler {

    public String handleThisRequest(Request request, Response response) throws DataAccessException {
        ListGamesService listGamesService = new ListGamesService();

        //ListGamesRequest listGamesRequest = toGson(request);


        FinalResponse finalResponse = listGamesService.getResponse(request.headers("authorization"));

        // set the response status
        setStatus(finalResponse.getMessage(), response);

        return fromGson(finalResponse);
    }

}
