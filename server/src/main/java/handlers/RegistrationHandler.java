package handlers;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import services.RegisterRequest;
import services.RegisterResponse;
import services.RegisterService;
import spark.Request;
import spark.Response;
import dataAccess.DataAccessException;


public class RegistrationHandler {

    private String handleThisRequest(Request request, Response response) throws DataAccessException{
        RegisterService registerService = new RegisterService(); // uh yes it is public

        RegisterRequest rRequest = toGson(request);

        RegisterResponse registerResponse = registerService.getResponse(rRequest);
        String jsonString = fromGson(registerResponse);


    }

    private String fromGson(RegisterResponse response) {
        Gson gson = new Gson();
        return gson.toJson(response);
    }
    private RegisterRequest toGson(Request request) {
        Gson gson = new Gson();
        RegisterRequest registerRequest = gson.fromJson(request.body(), RegisterRequest.class);
        return registerRequest;
    }
}
