package services;

import DataAccess.DataAccessException;
import dataAccess.*;
import responses.FinalResponse;

public class ListGamesService{

    public FinalResponse getResponse(String auth) throws DataAccessException {
        GameDAO gameDAO = new SqlGameDAO();

        AuthDAO authData = new SqlAuthDAO();

        FinalResponse finalResponse = new FinalResponse();
        try {
            // authorize the user
            if (authData.findAuth(auth)) {
                finalResponse.setGameList(gameDAO.getGameList());
            } else {
                finalResponse.setMessage("Error: unauthorized");
            }

            return finalResponse;
        }
        catch (DataAccessException exception) {
            finalResponse.setMessage(exception.getMessage());
            return finalResponse;
        }
    }
}
