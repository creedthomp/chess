package services;

import dataAccess.AuthDAO;
import DataAccess.DataAccessException;
import dataAccess.SqlAuthDAO;
import responses.FinalResponse;

public class LogoutService {

    public LogoutService(){}

    public FinalResponse getResponse(String authToken) throws DataAccessException {

        AuthDAO authData = new SqlAuthDAO();
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