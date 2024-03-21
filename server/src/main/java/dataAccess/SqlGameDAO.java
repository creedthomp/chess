package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import models.AuthTokenInformation;
import models.GameInformation;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class SqlGameDAO implements GameDAO {
    private static int ID = 1;
    public SqlGameDAO() throws DataAccessException {
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.configureDatabase(createStatements);
        }
        catch (SQLException ex) {
            throw new DataAccessException("this is stupid");
        }
    }

    public void addGame(GameInformation game) throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        game.setGameID(new Random().nextInt(10000));
        // I need to hash the password somewhere
        String gamejson = new Gson().toJson(game.getGame());
        var statement = "INSERT INTO game (gameID, whiteUser, blackUser, gameName, game) VALUES (?, ?, ?, ?, ?)";
        databaseManager.executeUpdate(statement, game.getGameID(), game.getWhiteName(), game.getBlackName(), game.getGameName(), gamejson);
    }

    @Override
    public HashSet<GameInformation> getGameList() throws DataAccessException {
        HashSet<GameInformation> authList = new HashSet<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUser, blackUser, gameName, game FROM game";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        authList.add(readGame(rs));
                    }
                }
            }
        }
        catch (Exception e) {
            throw new DataAccessException("Error: unable to read data");
        }
        return authList;
    }

    @Override
    public GameInformation getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM game WHERE gameID= ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        GameInformation thisgame = readGame(rs);
                        return thisgame;
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: bad request");
        }
        return null;
    }

    private GameInformation readGame(ResultSet rs) throws SQLException {
        GameInformation gameInformation = new GameInformation();
        var gameID = rs.getInt("gameID");
        var whiteUser = rs.getString("whiteUser");
        var blackUser = rs.getString("blackUser");
        var gameName = rs.getString("gameName");
        String gameJSON = rs.getString("game");
        ChessGame game = new Gson().fromJson(gameJSON, ChessGame.class);
        gameInformation.setGameID(gameID);
        gameInformation.setGameName(gameName);
        gameInformation.setWhiteName(whiteUser);
        gameInformation.setBlackName(blackUser);
        gameInformation.setGame(game);
        return gameInformation;
    }

    public void addWhite(int gameID, String username) throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        // I need to hash the password somewhere
        var statement = "UPDATE game SET whiteUser = ? WHERE gameID = ?";
        databaseManager.executeUpdate(statement, username, gameID);
    }

    public void addBlack(int gameID, String username) throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        // I need to hash the password somewhere
        var statement = "UPDATE game SET blackUser = ? WHERE gameID = ?";
        databaseManager.executeUpdate(statement, username, gameID);
    }
    public void clear() throws DataAccessException {
        ID = 1;
        DatabaseManager databaseManager = new DatabaseManager();
        var statement = "TRUNCATE game";
        databaseManager.executeUpdate(statement);

    }
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS game (
            `gameID` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
            `whiteUser` varchar(255) DEFAULT NULL,
            `blackUser` varchar(255) DEFAULT NULL,
            `gameName` varchar(255) NOT NULL,
            `game` BLOB DEFAULT NULL
            );
            """
    };

}
