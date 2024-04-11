package models;
import java.util.UUID;
public class AuthTokenInformation {

    String authToken;
    String username;

    public AuthTokenInformation(String username) {
        this.authToken = UUID.randomUUID().toString();
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
