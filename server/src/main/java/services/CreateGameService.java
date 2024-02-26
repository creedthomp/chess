package services;

import dataAccess.*;
import models.GameInformation;

public class CreateGameService {

    public FinalResponse getResponse(CreateGameRequest request, String auth) throws DataAccessException {
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        FinalResponse finalResponse = new FinalResponse();
        GameInformation gameInformation = new GameInformation();
        // ?try catch
        if (authDAO.findAuth(auth)) {
            if (request.getGameName() == null) {
                finalResponse.setMessage("Error: bad request");
                return finalResponse;
            }
            gameInformation.setGameName(request.getGameName());
            // add the game to the list of games
            gameDAO.addGame(gameInformation);
            finalResponse.setGameID(gameInformation.getGameID());
        }
        else {
            finalResponse.setMessage("Error: unauthorized");
            return finalResponse;
        }
        return finalResponse;

    }
}
