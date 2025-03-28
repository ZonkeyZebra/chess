package ui;

import exception.DataAccessException;
import model.LoginRequest;
import model.RegisterRequest;

import java.util.Arrays;

public class PreLoginClient {
    private final ServerFacade server;
    private final String serverUrl;
    private String authToken = null;

    public PreLoginClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) throws Exception {
        String[] tokens = input.split(" ");
        String command = tokens[0];
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (command) {
            case "login" -> login(params);
            case "register" -> register(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    public String login(String[] params) throws Exception {
        if (params.length == 2) {
            String username = params[0];
            try {
                var result = server.login(new LoginRequest(username, params[1]));
                setAuthToken(result.authToken());
                return String.format("Signed in as %s.", username);
            } catch (Exception e) {
                throw new Exception("Not a user");
            }
        }
        throw new Exception("Expected login <username> <password>");
    }

    public String register(String[] params) throws Exception {
        if (params.length == 3) {
            String username = params[0];
            try {
                var result = server.register(new RegisterRequest(username, params[1], params[2]));
                setAuthToken(result.authToken());
                return String.format("Registered as %s.", username);
            } catch (Exception e) {
                throw new Exception("User already taken");
            }
        }
        throw new DataAccessException("Expected register <username> <password> <email>");
    }

    public String help() {
        return """
                - login <username> <password>
                - register <username> <password> <email>
                - help
                - quit
                """;
    }

    private void setAuthToken(String token) {
        authToken = token;
    }

    public String getAuthToken() {
        return authToken;
    }
}
