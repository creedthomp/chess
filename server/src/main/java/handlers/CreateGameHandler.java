package handlers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import dataAccess.DataAccessException;
import services.CreateGameRequest;
import services.CreateGameService;
import services.FinalResponse;
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

        return toJsonExcludingZeroGameID(finalResponse);
    }

    public String toJsonExcludingZeroGameID(FinalResponse finalResponse) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // Only add the exclusion strategy if gameID is zero
        if (finalResponse.getGameID() == 0) {
            gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    // Exclude the gameID field
                    return "gameID".equals(f.getName());
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            });
        }

        Gson gson = gsonBuilder.create();
        return gson.toJson(finalResponse);
    }

}

