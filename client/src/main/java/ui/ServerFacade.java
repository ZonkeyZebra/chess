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
        return this.makeRequest("POST", path, request, RegisterResult.class);
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        String path = "/session";
        return this.makeRequest("POST", path, request, LoginResult.class);
    }

    public void logout(String authToken) throws DataAccessException {
        String path = String.format("/session/%s", authToken);
        this.makeRequest("DELETE", path, null, null);
    }

    public void clear() throws DataAccessException {
        String path = "/db";
        this.makeRequest("DELETE", path, null, null);
    }

    public void joinGame(JoinGameRequest request) throws DataAccessException {
        String path = "/game";
        this.makeRequest("PUT", path, request, null);
    }

    public ListGamesResult listGames() throws DataAccessException {
        String path = "/game";
        return this.makeRequest("GET", path, null, ListGamesResult.class);
    }

    public CreateGameResult createGame(CreateGameRequest request, String authToken) throws DataAccessException {
        String path = "/game";
        return this.makeRequest("POST", path, request, CreateGameResult.class);
    }

    ///Get requests never have a body?

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws DataAccessException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (DataAccessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
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
        if (!isSuccessful(status)) {
            try (InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    throw new DataAccessException(respErr.toString());
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
