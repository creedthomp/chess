package handlers;

import dataAccess.DataAccessException;
import services.ClearService;
import services.FinalResponse;
import spark.Request;
import spark.Response;

public class ClearHandler extends ParentHandler {

    public String handleThisRequest(Request request, Response response) throws DataAccessException {
        ClearService clearService = new ClearService();

        FinalResponse finalResponse = clearService.getResponse(request.headers("authorization"));

        response.status(200);

        return toJsonExcludingZeroGameID(finalResponse);
    }
}
