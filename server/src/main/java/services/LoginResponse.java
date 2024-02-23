package services;

public class LoginResponse {
    String username;

    String authT;

    String message;

    LoginResponse() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthT() {
        return authT;
    }

    public void setAuthT(String authT) {
        this.authT = authT;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
