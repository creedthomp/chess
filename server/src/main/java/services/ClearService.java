package services;

import DataAccess.DataAccessException;
import dataAccess.*;
import responses.FinalResponse;

public class ClearService {
    public FinalResponse clearData() throws DataAccessException {
        AuthDAO authDAO = new SqlAuthDAO();
        GameDAO gameDAO = new SqlGameDAO();
        UserDAO userDAO = new SqlUserDAO();
        FinalResponse finalResponse = new FinalResponse();

       authDAO.clear();
       gameDAO.clear();
       userDAO.clear();


       return finalResponse;
    }
}
