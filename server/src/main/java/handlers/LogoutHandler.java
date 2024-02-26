package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import services.*;
import spark.Request;
import spark.Response;

public class LogoutHandler extends ParentHandler {

    public String handleThisRequest(Request request, Response response) throws DataAccessException {
        LogoutService logoutService = new LogoutService();

        //LogoutRequest logoutRequest = toGson(request);

        FinalResponse finalResponse = logoutService.getResponse(request.headers("authorization"));

        // set the response status
        setStatus(finalResponse.getMessage(), response);

        return fromGson(finalResponse);
    }


}
