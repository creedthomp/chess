package handlers;

import com.google.gson.Gson;
import services.RegisterRequest;
import services.FinalResponse;
import services.RegisterService;
import spark.Request;
import spark.Response;
import dataAccess.DataAccessException;


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
