package services;

import dataAccess.*;
import models.GameInformation;

public class JoinGameService {

    public FinalResponse getResponse(JoinGameRequest request, String auth) throws DataAccessException {
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        FinalResponse finalResponse = new FinalResponse();

        // I need to ADD A SPECTATOR THING???/
        try {
            if (authDAO.findAuth(auth)) {
                GameInformation game = gameDAO.getGame(request.gameID);
                // set the team color
                if (request.teamColor.equals("WHITE")) {
                    // make sure the color is empty
                    if (game.getWhiteName() == null) {
                        finalResponse.setMessage("Error: already taken");
                        return finalResponse;
                    }
                    game.setWhiteName(authDAO.findUsername(auth));
                } else if (request.teamColor.equals("BLACK")) {
                    if (game.getBlackName() == null) {
                        finalResponse.setMessage("Error: already taken");
                        return finalResponse;
                    }
                    game.setBlackName(authDAO.findUsername(auth));
                }
                // trying to make an observer
                else if (request.teamColor == null) {
                    game.setObserver(authDAO.findUsername(auth));
                } else {
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
