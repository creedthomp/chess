package models;

import chess.ChessGame;

public class GameInformation {

     int gameID;
    String whiteUsername;
    String blackUsername;
    String gameName;
    ChessGame game;

    public GameInformation() {
        game = new ChessGame();
        //gameID += 1;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameD) {
        gameID = gameD;

    }

    public String getWhiteName() {
        return whiteUsername;
    }

    public void setWhiteName(String whiteName) {
        this.whiteUsername = whiteName;
    }

    public String getBlackName() {
        return blackUsername;
    }

    public void setBlackName(String blackName) {
        this.blackUsername = blackName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }
}
