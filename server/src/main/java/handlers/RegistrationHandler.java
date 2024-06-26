package handlers;

import com.google.gson.Gson;
import requests.RegisterRequest;
import responses.FinalResponse;
import services.RegisterService;
import spark.Request;
import spark.Response;
import DataAccess.DataAccessException;


public class RegistrationHandler extends ParentHandler {




    public String handleThisRequest(Request request, Response response) throws DataAccessException{
        try {
            Gson gson = new Gson();
            RegisterService registerService = new RegisterService();

            RegisterRequest rRequest = gson.fromJson(request.body(), RegisterRequest.class);

            FinalResponse finalResponse = registerService.getResponse(rRequest);

            // set the response status
            setStatus(finalResponse.getMessage(), response);

            return toJsonExcludingZeroGameID(finalResponse);
        }
        catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }

    }

}
