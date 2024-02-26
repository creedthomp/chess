package handlers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import services.CreateGameRequest;
import services.FinalResponse;
import spark.Request;
import spark.Response;

public class ParentHandler {

    public void setStatus(String string, Response response) {
        if (string== null) {
            response.status(200);
        }
        else if (string.equals("Error: unauthorized")) {
            response.status(401);
        }
        else if (string.equals("Error: already taken")) {
            response.status(403);
        }
        else if (string.equals("Error: bad request")) {
            response.status(400);
        }
        else{
            response.status(500);
        }
    }

    public String fromGson(FinalResponse response) {
        Gson gson = new Gson();
        return gson.toJson(response);
    }

    public String toGsonHead(Request request) {
        return request.headers("authorization");
    }

    String toJsonExcludingZeroGameID(FinalResponse finalResponse) {
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
