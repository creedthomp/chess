package services;

import dataAccess.*;

public class ListGamesService {

    public ListGamesResponse getResponse(ListGamesRequest request) throws DataAccessException {
        GameDAO gameDAO = new MemoryGameDAO();

        AuthDAO authData = new MemoryAuthDAO();

        ListGamesResponse listGamesResponse = new ListGamesResponse();

        // authorize the user
        if (authData.findAuth(request.authT)) {
            listGamesResponse.setGameList(gameDAO.getGameList());
        }
        else {
            listGamesResponse.setMessage("Error: unauthorized");
        }

        return listGamesResponse;
    }
}
