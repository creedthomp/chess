package ui;

import com.google.gson.Gson;
import DataAccess.DataAccessException;
import requests.RegisterRequest;
import responses.FinalResponse;
import requests.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
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
            throw new DataAccessException(e.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException, IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) { //here is the error
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, DataAccessException {
        var status = http.getResponseCode();
        if (status == 403) {
            throw new DataAccessException("Error: already taken");
//            throw new DataAccessException(status , "Error: already taken");
        }
        if (!isSuccessful(status)) {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);

                    FinalResponse response = new Gson().fromJson(reader, FinalResponse.class);
                throw new DataAccessException("failure: " + response.getMessage());
            }
            // should I change the format to be like the ones above
            //throw new DataAccessException("failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
