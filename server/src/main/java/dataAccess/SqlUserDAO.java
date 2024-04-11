package dataAccess;

import DataAccess.DataAccessException;
import models.UserInformation;
import java.sql.*;

public class SqlUserDAO implements UserDAO {

    public SqlUserDAO() throws DataAccessException {
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.configureDatabase(createStatements);
        }
        catch (SQLException ex) {
            throw new DataAccessException("this is dumb");
        }
    }

    public void createUser(UserInformation user) throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        // I need to hash the password somewhere
        var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        databaseManager.executeUpdate(statement, user.getUsername(), user.getPassword(), user.getEmail());
    }

    public boolean findUsername(UserInformation user) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = "SELECT username FROM user WHERE username = ?";
            try (var ps = connection.prepareStatement(statement)) {
                ps.setString(1, user.getUsername());
                try (var rs = ps.executeQuery()) {
                    //returns false if we go through the whole thing and dont find it
                    return rs.next();
                    }
                }
            }
        catch (SQLException exception) {
            throw new DataAccessException("Error: user not found");
        }
    }

    public boolean loginUser(String username, String password) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password FROM user WHERE username = ? AND password = ?;";
            try (var ps = connection.prepareStatement(statement)) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (var rs = ps.executeQuery()) {
                    //returns false if we go through the whole thing and dont find it
                    return rs.next();
                }
            }
        }
        catch (SQLException exception) {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public void clear() throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        var statement = "TRUNCATE user";
        databaseManager.executeUpdate(statement);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS user (
            `username` varchar(255) NOT NULL,
            `password` varchar(255) NOT NULL,
            `email` varchar(255) NOT NULL,
            PRIMARY KEY(`username`)
            );
            """
    };


}
