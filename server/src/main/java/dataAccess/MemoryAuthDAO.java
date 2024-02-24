package dataAccess;

import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO{


    static public HashSet<String> authList = new HashSet<>();

    @Override
    public boolean findAuth(String token) throws DataAccessException {
        return authList.contains(token);
    }

    @Override
    public void addAuth(String token) throws DataAccessException {
        authList.add(token);
    }

    @Override
    public void removeAuth(String token) throws DataAccessException {
        authList.remove(token);
    }

}
