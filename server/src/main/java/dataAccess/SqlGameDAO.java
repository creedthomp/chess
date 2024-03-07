package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import models.AuthTokenInformation;
import models.GameInformation;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class SqlGameDAO implements GameDAO {

    public SqlGameDAO() throws DataAccessException {
        configureDatabase();
    }

    public void addGame(GameInformation game) throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        // I need to hash the password somewhere
        var statement = "INSERT INTO game (gameID, whiteUser, blackUser, gameName, game) VALUES (?, ?, ?, ?, ?)";
        databaseManager.executeUpdate(statement, game.getGameID(), game.getWhiteName(), game.getBlackName(), game.getGameName(), game.getGame());
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
            var statement = "SELECT gameID, whiteUser, blackUser, gameName, game From game WHERE gameID= ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: unable to read data");
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

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error: unable to configure database");
        }
    }
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS game (
            `gameID` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
            `whiteUser` varchar(255) DEFAULT NULL,
            `blackUser` varchar(255) DEFAULT NULL,
            `gameName` varchar(255) NOT NULL
            `game` BLOB DEFAULT NULL
            PRIMARY KEY(`gameID`)
            );
            """
    };
}
