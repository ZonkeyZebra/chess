package ui;

import dataaccess.DataAccessException;

import java.util.Arrays;

public class PreLoginClient {
    private final ServerFacade server;
    private final String serverUrl;

    public PreLoginClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) throws DataAccessException {
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

    public String login(String[] params) throws DataAccessException {
        if (params.length >= 1) {
            String username = params[0];
            return String.format("Signed in as %s", username);
        }
        throw new DataAccessException("Expected: login <username> <password>");
    }

    public String register(String[] params) throws DataAccessException {
        if (params.length >= 2) {
            String username = params[0];
            return String.format("Registered as %s", username);
        }
        throw new DataAccessException("Expected: register <username> <password> <email>");
    }

    public String help() {
        return """
                - login <username> <password>
                - register <username> <password> <email>
                - quit
                """;
    }
}
