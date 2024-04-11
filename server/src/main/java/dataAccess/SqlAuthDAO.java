package dataAccess;

import DataAccess.DataAccessException;
import models.AuthTokenInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class SqlAuthDAO implements AuthDAO {

    public SqlAuthDAO() throws DataAccessException {
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.configureDatabase(createStatements);
        }
        catch (SQLException ex) {
            throw new DataAccessException("this is stupid");
        }
    }

    public boolean findAuth(String token) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = "SELECT authtoken FROM auth WHERE authtoken = ?";
            try (var ps = connection.prepareStatement(statement)) {
                ps.setString(1, token);
                try (var rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        }
        catch (SQLException exception) {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public HashSet<AuthTokenInformation> getAuthList() throws DataAccessException {
        HashSet<AuthTokenInformation> authList = new HashSet<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authtoken FROM auth";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        authList.add(readAuthList(rs));
                    }
                }
            }
        }
        catch (Exception e) {
            throw new DataAccessException("Error: unable to read data");
        }
        return authList;
    }

    private AuthTokenInformation readAuthList(ResultSet rs) throws SQLException {
        var authtoken = rs.getString("authtoken");
        var username = rs.getString("username");
        AuthTokenInformation authTokenInformation = new AuthTokenInformation(username);
        authTokenInformation.setUsername(username);
        authTokenInformation.setAuthToken(authtoken);
        return authTokenInformation;
    }

    public void addAuth(AuthTokenInformation token) throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        var statement = "INSERT INTO auth (username, authtoken) VALUES (?, ?)";
        databaseManager.executeUpdate(statement, token.getUsername(), token.getAuthToken());
    }

    public void removeAuth(String token) throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        var statement = "DELETE FROM auth WHERE authtoken=?";
        // what happens if I try to delete something that is not there?
        databaseManager.executeUpdate(statement, token);
    }

    public String getUsername(String token) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username From auth WHERE authtoken= ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, token);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUsername(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: unable to read data");
        }
        return null;
    }

    private String readUsername(ResultSet rs) throws SQLException {
        var username= rs.getString("username");
        return username;
    }

    public String getAuth(String user) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authtoken From auth WHERE username= ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, user);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: unable to read data");
        }
        return null;
    }

    private String readAuth(ResultSet rs) throws SQLException {
        var authtoken = rs.getString("authtoken");
        return authtoken;
    }

    public void clear() throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        var statement = "TRUNCATE auth";
        databaseManager.executeUpdate(statement);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS auth (
            `username` varchar(255) NOT NULL,
            `authtoken` varchar(255) NOT NULL,
            PRIMARY KEY(`authtoken`, `username`)
            );
            """
    };
}
