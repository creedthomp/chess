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

    public HashSet<AuthTokenInformation> getAuthList() {
        return authList;
    }

    public static void setAuthList(HashSet<AuthTokenInformation> authList) {
        MemoryAuthDAO.authList = authList;
    }

    @Override
    public void addAuth(AuthTokenInformation token){
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
    public String findUsername(String token) throws DataAccessException {
        for (AuthTokenInformation info : authList) {
            if (info.getAuthToken().equals(token)) {
                return info.getUsername();
            }
        }
        throw new DataAccessException("Error: unauthorized");
    }

    @Override
    public String findAuthT(String user) throws DataAccessException {
        for (AuthTokenInformation info : authList) {
            if (info.getUsername().equals(user)) {
                return info.getAuthToken();
            }
        }
        throw new DataAccessException("Error: unauthorized");
    }

//    public boolean findInfo(String username) {
//        for (AuthTokenInformation info : authList) {
//            if (info.getUsername().equals(username)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void removeUsername(String username) {
//        AuthTokenInformation removeThis = null;
//        for (AuthTokenInformation info : authList) {
//            if (info.getUsername().equals(username)) {
//                removeThis = info;
//            }
//        }
//        if (!(removeThis == null)) {
//            authList.remove(removeThis);
//        }
//    }


    public void clear() {
        authList.clear();
    }
}
