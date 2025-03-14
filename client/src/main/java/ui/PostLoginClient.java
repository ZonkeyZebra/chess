package ui;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.CreateGameRequest;
import model.JoinGameRequest;

import java.util.Arrays;
import java.util.Objects;

public class PostLoginClient {
    private final ServerFacade server;
    private final String serverUrl;

    public PostLoginClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input, String authToken) throws DataAccessException {
        String[] tokens = input.split(" ");
        String command = tokens[0];
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (command) {
            case "logout" -> logout(authToken);
            case "create" -> createGame(params);
            case "list" -> listGames();
            case "join" -> joinGame(params);
            case "observe" -> observeGame(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    public String logout(String authToken) throws DataAccessException {
        try {
            server.logout(authToken);
            return "See you later!";
        } catch (DataAccessException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public String createGame(String[] params) throws DataAccessException {
        if (params.length >= 1) {
            var result = server.createGame(new CreateGameRequest(params[0]));
            return String.format("Created game! Here is its id: %d", result.gameID());
        }
        throw new DataAccessException("Expected: create <name>");
    }

    public String listGames() throws DataAccessException {
        try {
            var result = server.listGames();
            //return String.format("Games: %s", result.games());
            return result.games().toString();
        } catch (DataAccessException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public String joinGame(String[] params) throws DataAccessException {
        if (params.length >= 2 && (Objects.equals(params[1], "black") || Objects.equals(params[1], "white"))) {
            int id = Integer.parseInt(params[0]);
            ChessGame.TeamColor teamColor = ChessGame.TeamColor.WHITE;
            if (params[1].equals("black")) {
                teamColor = ChessGame.TeamColor.BLACK;
            }
            server.joinGame(new JoinGameRequest(teamColor, id));
            return new DrawBoard().toString();
        }
        throw new DataAccessException("Expected: join <id> <white|black>");
    }

    public String observeGame(String[] params) {
        return "TODO: observeGame";
    }

    public String help() {
        return """
                - logout
                - create <name>
                - list
                - join <id> <white|black>
                - observe <id>
                - quit
                """;
    }
}
