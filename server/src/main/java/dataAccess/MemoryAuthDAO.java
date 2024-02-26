package dataAccess;

import models.AuthTokenInformation;

import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO{


    static public HashSet<AuthTokenInformation> authList = new HashSet<>();

    @Override
    public boolean findAuth(String token) throws DataAccessException {
        for (AuthTokenInformation info : authList) {
            if (info.getAuthToken().equals(token)) {
                return true;
            }
        }
        throw new DataAccessException("Error: unauthorized");
    }

    @Override
    public void addAuth(AuthTokenInformation token) throws DataAccessException {
        authList.add(token);
    }

    @Override
    public void removeAuth(String token) throws DataAccessException {
        AuthTokenInformation removeThis = null;
        for (AuthTokenInformation info : authList) {
            if (info.getAuthToken().equals(token)) {
                removeThis = info;
            }
        }
        if (!(removeThis == null)) {
            authList.remove(removeThis);
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    @Override
    public String printAuth() throws DataAccessException {
        String thi = "";
//        for (String thing : authList) {
//            thi += thing;
//        }
        return thi;
    }

    @Override
    public String findUsername(String token) throws DataAccessException {
        for (AuthTokenInformation info : authList) {
            if (info.getAuthToken().equals(token)) {
                return info.getUsername();
            }
        }
        throw new DataAccessException("Error: unauthorized");
    }


}
