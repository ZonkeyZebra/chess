package ui;

import java.util.Arrays;

public class PostLoginClient {
    private final ServerFacade server;
    private final String serverUrl;

    public PostLoginClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        String[] tokens = input.split(" ");
        String command = tokens[0];
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (command) {
            case "logout" -> logout();
            case "createGame" -> createGame(params);
            case "list" -> listGames();
            case "join" -> joinGame(params);
            case "observe" -> observeGame(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    public String logout() {
        return "TODO: logout";
    }

    public String createGame(String[] params) {
        return "TODO: createGame";
    }

    public String listGames() {
        return "TODO: listGames";
    }

    public String joinGame(String[] params) {
        return "TODO: joinGame";
    }

    public String observeGame(String[] params) {
        return "TODO: observeGame";
    }

    public String help() {
        return """
                - logout
                - createGame <name>
                - list
                - join <id> <white|black>
                - observe <id>
                - quit
                """;
    }
}
