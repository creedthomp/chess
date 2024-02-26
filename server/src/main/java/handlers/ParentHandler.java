package handlers;

import com.google.gson.Gson;
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
}
