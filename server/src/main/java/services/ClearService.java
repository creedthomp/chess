package services;

import dataAccess.*;

public class ClearService {
    public FinalResponse getResponse(String authToken) throws DataAccessException {
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        UserDAO userDAO = new MemoryUserDAO();
        FinalResponse finalResponse = new FinalResponse();
       authDAO.clear();
       gameDAO.clear();
       userDAO.clear();

       return finalResponse;
    }
}
