package services;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import responses.FinalResponse;

public class LogoutService {

    public LogoutService(){}

    public FinalResponse getResponse(String authToken) throws DataAccessException {

        MemoryAuthDAO authData = new MemoryAuthDAO();
        FinalResponse finalResponse = new FinalResponse();
        try {
            // is the authToken authorized?
            if ((authData.findAuth(authToken))) {
                authData.removeAuth(authToken);
            } else {
                finalResponse.setMessage("Error: unauthorized");
            }

            return finalResponse;
        }
        catch(DataAccessException exception) {
            finalResponse.setMessage(exception.getMessage());
            return finalResponse;
        }

    }

}