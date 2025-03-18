package ui;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * PreLogin UI phase
 * PostLogin UI Phase
 * Game UI Phase
 * in petshop repl runs and then calls commands from PetClient
 * ANSI Escape Codes on Wikipedia will be a good resource
 */

public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        String path = "/user";
        return this.makeRequest("POST", path, request, null, RegisterResult.class);
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        String path = "/session";
        return this.makeRequest("POST", path, request, null, LoginResult.class);
    }

    public void logout(String authToken) throws DataAccessException {
        String path = "/session";
        this.makeRequest("DELETE", path, null, authToken, null);
    }

    public void clear() throws DataAccessException {
        String path = "/db";
        this.makeRequest("DELETE", path, null, null, null);
    }

    public void joinGame(JoinGameRequest request, String authToken) throws DataAccessException {
        String path = "/game";
        this.makeRequest("PUT", path, request, authToken, null);
    }

    public ListGamesResult listGames(String authToken) throws DataAccessException {
        String path = "/game";
        return this.makeRequest("GET", path, null, authToken, ListGamesResult.class);
    }

    public CreateGameResult createGame(CreateGameRequest request, String authToken) throws DataAccessException {
        String path = "/game";
        return this.makeRequest("POST", path, request, authToken, CreateGameResult.class);
    }

    ///Get requests never have a body?

    private <T> T makeRequest(String method, String path, Object request, String authToken, Class<T> responseClass) throws DataAccessException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http, authToken);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http, String authToken) throws IOException {
        if (request != null && authToken == null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        } else if (request == null) {
            http.addRequestProperty("Authorization", authToken);
        } else {
            http.addRequestProperty("Content-Type", "application/json");
            http.addRequestProperty("Authorization", authToken);
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, DataAccessException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            try (InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    throw new DataAccessException(String.format(respErr.toString() + " status: " + status));
                }
            }

            throw new DataAccessException("other failure: " + status);
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
