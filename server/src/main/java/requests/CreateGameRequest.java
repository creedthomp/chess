package requests;

public class CreateGameRequest {
    String authT;

    String gameName;

    public CreateGameRequest() {
    }

    public String getAuthT() {
        return authT;
    }

    public void setAuthT(String authT) {
        this.authT = authT;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gamName) {
        this.gameName = gamName;
    }
}
