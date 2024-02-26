package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import services.*;
import spark.Request;
import spark.Response;

public class CreateGameHandler extends ParentHandler {

    public String handleThisRequest(Request request, Response response) throws DataAccessException {
        Gson gson = new Gson();

        CreateGameService createGameService = new CreateGameService();

        String head = toGsonHead(request);

        CreateGameRequest createGameRequest = gson.fromJson(request.body(), CreateGameRequest.class);

        FinalResponse finalResponse = new CreateGameService().getResponse(createGameRequest, head);

        // set the response status
        setStatus(finalResponse.getMessage(), response);

        return fromGson(finalResponse);
    }

}

