package handlers;

import dataAccess.DataAccessException;
import services.ClearService;
import responses.FinalResponse;
import spark.Request;
import spark.Response;

public class ClearHandler extends ParentHandler {

    public String handleThisRequest(Request request, Response response) throws DataAccessException {
        ClearService clearService = new ClearService();

        FinalResponse finalResponse = clearService.clearData();

        response.status(200);

        return toJsonExcludingZeroGameID(finalResponse);
    }
}
