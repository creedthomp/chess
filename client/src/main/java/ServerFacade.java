
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import requests.RegisterRequest;
import responses.*;
import responses.FinalResponse;
import requests.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;


public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public FinalResponse register(RegisterRequest request) throws DataAccessException {
        return this.makeRequest("POST", "/user", request, FinalResponse.class, null);
    }

    public FinalResponse login(LoginRequest request) throws DataAccessException {
        return this.makeRequest("POST", "/session", request, FinalResponse.class, null);
    }

    public FinalResponse logout(FinalResponse response) throws DataAccessException {
        return this.makeRequest("DELETE", "/session", response, FinalResponse.class, response);
    }

    public FinalResponse createGame(CreateGameRequest request, FinalResponse response) throws DataAccessException {
        return this.makeRequest("POST", "/game", request, FinalResponse.class, response);
    }

    public FinalResponse ListGames(FinalResponse response) throws DataAccessException {
        return this.makeRequest("GET", "/game", response, FinalResponse.class, response);
    }

    public FinalResponse joinGame(JoinGameRequest request, FinalResponse response) throws DataAccessException {
        return this.makeRequest("PUT", "/game", request, FinalResponse.class, response);
    }


// is this needed?
//    public FinalResponse clear() {
//        return this.makeRequest("PUT", "/game", request, FinalResponse.class, response);
//    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, FinalResponse response) throws DataAccessException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            // add the authtoken to the header
            if (response != null) {
                http.addRequestProperty("authorization", response.getAuthToken());
            }
            if (!Objects.equals(method, "GET")) {
                http.setDoOutput(true);
                writeBody(request, http);
            }

            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        }
        catch (Exception e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException, IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, DataAccessException {
        var status = http.getResponseCode();
        if (status == 403) {
            throw new ResponseException(status, "Error: already taken");
        }
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {

    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
