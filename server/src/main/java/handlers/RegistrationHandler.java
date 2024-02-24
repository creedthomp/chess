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




    public String handleThisRequest(Request request, Response response) throws DataAccessException{
        try {
            RegisterService registerService = new RegisterService();

            RegisterRequest rRequest = toGson(request);

            RegisterResponse registerResponse = registerService.getResponse(rRequest);

            if (registerResponse.getMessage() == null) {
                response.status(200);
            }

            else if (registerResponse.getMessage().equals("Error: already taken")) {
                response.status(403);
            }
            else if (registerResponse.getMessage().equals("Error: bad request")) {
                response.status(400);
            }
            else{
                response.status(500);
            }

            return fromGson(registerResponse);
        }
        catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
//        if (registerResponse.getMessage() != null) {
//            if (registerResponse.getMessage().equals("Error: bad request")){
//                response.status(400);
//            }
//            if (registerResponse.getMessage().equals("Error: already taken")) {
//                response.status(403);
//            }
//            else{
//                response.status(500);
//            }
//        }
//        else{
//            response.status(200);
//        }

//        return fromGson(registerResponse);

    }

    private String fromGson(RegisterResponse response) {
        Gson gson = new Gson();
        return gson.toJson(response);
    }
    private RegisterRequest toGson(Request request) {
        Gson gson = new Gson();
        return gson.fromJson(request.body(), RegisterRequest.class);
    }
}
