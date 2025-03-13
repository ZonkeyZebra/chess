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
            case "createGame" -> createGame();
            case "list" -> listGames();
            case "join" -> joinGame();
            case "observe" -> observeGame();
            case "quit" -> "quit";
            default -> help();
        };
    }

    public String logout() {
        return "TODO: logout";
    }

    public String createGame() {
        return "TODO: createGame";
    }

    public String listGames() {
        return "TODO: listGames";
    }

    public String joinGame() {
        return "TODO: joinGame";
    }

    public String observeGame() {
        return "TODO: observeGame";
    }

    public String help() {
        return """
                - logout
                - createGame <gameName>
                - list
                - join <id>
                - observe <id>
                - quit
                """;
    }
}
