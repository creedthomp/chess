package services;

import dataAccess.*;
import models.GameInformation;
import requests.JoinGameRequest;
import responses.FinalResponse;

import java.util.Objects;

public class JoinGameService {

    public FinalResponse getResponse(JoinGameRequest request, String auth) throws DataAccessException {
        AuthDAO authDAO = new SqlAuthDAO();
        GameDAO gameDAO = new SqlGameDAO();
        FinalResponse finalResponse = new FinalResponse();


        try {
            if (authDAO.findAuth(auth)) {
                GameInformation game = gameDAO.getGame(request.gameID);
                if (game == null) {
                    finalResponse.setMessage("Error: bad request");
                    return finalResponse;
                }
                if (request.playerColor == null || request.playerColor.isEmpty()) {
                    // make sure the color is empty
                    return finalResponse;
                }
                else if (Objects.equals(request.playerColor, "BLACK")) {
                    // if the color is already taken
                    if (!(game.getBlackName() == null)) {
                        finalResponse.setMessage("Error: already taken");
                        return finalResponse;
                    }
                    gameDAO.addBlack(request.gameID, authDAO.getUsername(auth));
                    game.setBlackName(authDAO.getUsername(auth));
                }
                else if ((Objects.equals(request.playerColor, "WHITE"))) {
                    // if the color is already taken
                    if (!(game.getWhiteName() == null)) {
                        finalResponse.setMessage("Error: already taken");
                        return finalResponse;
                    }
                    gameDAO.addWhite(request.gameID, authDAO.getUsername(auth));
                    game.setWhiteName(authDAO.getUsername(auth));
                }
                else {
                    finalResponse.setMessage("Error: bad request");
                }
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
