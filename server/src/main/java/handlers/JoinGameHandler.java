package handlers;

import com.google.gson.Gson;
import DataAccess.DataAccessException;
import responses.FinalResponse;
import requests.JoinGameRequest;
import spark.Request;
import spark.Response;
import services.JoinGameService;

public class JoinGameHandler extends ParentHandler {

    public String handleThisRequest(Request request, Response response) throws DataAccessException {
        Gson gson = new Gson();
        JoinGameService joinGameService = new JoinGameService();

        String auth = toGsonHead(request);

        JoinGameRequest joinGameRequest = gson.fromJson(request.body(), JoinGameRequest.class);

        FinalResponse finalResponse = joinGameService.getResponse(joinGameRequest, auth);

        setStatus(finalResponse.getMessage(), response);

        return toJsonExcludingZeroGameID(finalResponse);
    }
}
