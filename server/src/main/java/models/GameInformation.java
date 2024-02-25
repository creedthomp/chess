package models;

public class GameInformation {
    int gameID;
    String whiteName;
    String blackName;
    String gameName;

    GameInformation(int gameID, String whiteName, String blackName, String gameName) {
        this.blackName = blackName;
        this.whiteName = whiteName;
        this.gameID = gameID;
        this.gameName = gameName;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
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
}
