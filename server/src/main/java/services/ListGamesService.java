package services;

import dataAccess.*;

public class ListGamesService {

    public FinalResponse getResponse(String auth) throws DataAccessException {
        GameDAO gameDAO = new MemoryGameDAO();

        AuthDAO authData = new MemoryAuthDAO();

        FinalResponse finalResponse = new FinalResponse();

        // authorize the user
        if (authData.findAuth(auth)) {
            finalResponse.setGameList(gameDAO.getGameList());
        }
        else {
            finalResponse.setMessage("Error: unauthorized");
        }

        return finalResponse;
    }
}
