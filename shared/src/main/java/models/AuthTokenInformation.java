package models;
import java.util.UUID;
public class AuthTokenInformation {

    String AuthToken;
    String username;

    public AuthTokenInformation(String username) {
        this.AuthToken = UUID.randomUUID().toString();
        this.username = username;
    }
    public String getAuthToken() {
        return AuthToken;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
