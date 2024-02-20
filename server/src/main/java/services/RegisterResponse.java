package services;

public class RegisterResponse {

    String authT;
    String message;
    String username;


    // construct
    RegisterResponse() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
