package services;

import dataAccess.*;
import models.GameInformation;
import requests.CreateGameRequest;
import responses.FinalResponse;

public class CreateGameService {

    public FinalResponse getResponse(CreateGameRequest request, String auth) throws DataAccessException {
        AuthDAO authDAO = new SqlAuthDAO();
        GameDAO gameDAO = new SqlGameDAO();
        FinalResponse finalResponse = new FinalResponse();
       // CreateGameResponse createGameResponse = new CreateGameResponse();
        GameInformation gameInformation = new GameInformation();

        try {
            if (authDAO.findAuth(auth)) {
                if (request.getGameName() == null) {
                    finalResponse.setMessage("Error: bad request");
                    return finalResponse;
                }
                gameInformation.setGameName(request.getGameName());
                //gameInformation.setGameID(gameInformation.getGameID());
                // add the game to the list of games
                gameDAO.addGame(gameInformation);
                int id = gameInformation.getGameID();

                finalResponse.setGameID(id);
            } else {
                finalResponse.setMessage("Error: unauthorized");
                return finalResponse;
            }
            return finalResponse;
        }
        catch (DataAccessException exception) {
            finalResponse.setMessage(exception.getMessage());
            return finalResponse;
        }

    }
}
