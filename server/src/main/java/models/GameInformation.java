package models;

import chess.ChessGame;

public class GameInformation {
    private static int gameID;
    String whiteName;
    String blackName;
    String gameName;
    String observer;
    ChessGame game;

    public GameInformation() {
        game = new ChessGame();
        gameID +=1;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        GameInformation.gameID = gameID;
    }

    public String getWhiteName() {
        return whiteName;
    }

    public void setWhiteName(String whiteName) {
        this.whiteName = whiteName;
    }

    public String getBlackName() {
        return blackName;
    }

    public void setBlackName(String blackName) {
        this.blackName = blackName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getObserver() {
        return observer;
    }

    public void setObserver(String observer) {
        this.observer = observer;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }
}
