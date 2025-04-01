package ui;

import chess.ChessBoard;
import chess.ChessGame;
import dataaccess.GameDAO;
import dataaccess.MySqlGameDAO;
import exception.DataAccessException;
import model.CreateGameRequest;
import model.GameData;
import model.JoinGameRequest;
import ui.websocket.GameHandler;
import ui.websocket.WebSocketFacade;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class PostLoginClient {
    private final ServerFacade server;
    private final String serverUrl;
    private final HashMap<Integer, Integer> idList = new HashMap<>();
    private final GameDAO gameDataAccess = new MySqlGameDAO();
    private ChessBoard gameBoard;
    private WebSocketFacade ws;
    private final GameHandler handler;

    public PostLoginClient(String serverUrl, GameHandler handler) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.handler = handler;
    }

    public String eval(String input, String authToken) throws Exception {
        String[] tokens = input.split(" ");
        String command = tokens[0];
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (command) {
            case "logout" -> logout(authToken);
            case "create" -> createGame(params, authToken);
            case "list" -> listGames(authToken);
            case "join" -> joinGame(params, authToken);
            case "observe" -> observeGame(params, authToken);
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

    public String createGame(String[] params, String authToken) throws DataAccessException {
        if (params.length >= 1) {
            var result = server.createGame(new CreateGameRequest(params[0]), authToken);
            return "Created game!";
        }
        throw new DataAccessException("Expected create <name>");
    }

    public String listGames(String authToken) throws DataAccessException {
        try {
            var result = server.listGames(authToken);
            var resultArray = result.games().toArray();
            int listNum = 1;
            int gameID;
            String whiteUser;
            String blackUser;
            String gameName;
            String gameList = "";
            for (int i = 0; i < resultArray.length; i++) {
                gameID = ((GameData) resultArray[i]).gameID();
                idList.put(listNum, gameID);
                whiteUser = ((GameData) resultArray[i]).whiteUsername();
                blackUser = ((GameData) resultArray[i]).blackUsername();
                gameName = ((GameData) resultArray[i]).gameName();
                if (whiteUser == null) {
                    whiteUser = "<empty>";
                }
                if (blackUser == null) {
                    blackUser = "<empty>";
                }
                gameList = String.format("\u001B[34m" + "%d. Name: %s | White: %s | Black: %s", listNum, gameName, whiteUser, blackUser);
                listNum++;
                System.out.println(gameList);
            }
            return "";
        } catch (DataAccessException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public String joinGame(String[] params, String authToken) throws DataAccessException {
        if (params.length == 2 && (Objects.equals(params[1], "black") || Objects.equals(params[1], "white"))) {
            int num = Integer.parseInt(params[0]);
            int id = 0;
            try {
                id = idList.get(num);
            } catch (Exception e) {
                throw new DataAccessException("This game does not exist.");
            }
            ChessGame.TeamColor teamColor = ChessGame.TeamColor.WHITE;
            if (params[1].equals("black")) {
                teamColor = ChessGame.TeamColor.BLACK;
            }
            try {
                server.joinGame(new JoinGameRequest(teamColor, id), authToken);
                ChessBoard board = gameDataAccess.getGame(id).game().getBoard();
                setBoard(board);
                return "Draw Board: " + teamColor + " " + num;
            } catch (DataAccessException e) {
                throw new DataAccessException("Spot is already taken. Join another game or as another color.");
            }
        }
        throw new DataAccessException("Expected join <id> <white|black>");
    }

    public String observeGame(String[] params, String authToken) throws Exception {
        if (params.length >= 1) {
            int id = Integer.parseInt(params[0]);
            ChessBoard board = gameDataAccess.getGame(id).game().getBoard();
            setBoard(board);
            return "Draw Board: observe " + id;
        }
        throw new DataAccessException("Expected observe <id>");
    }

    public String help() {
        return """
                - logout
                - create <name>
                - list
                - join <id> <white|black>
                - observe <id>
                - help
                - quit
                """;
    }

    private void setBoard(ChessBoard board) {
        this.gameBoard = board;
    }

    public ChessBoard getGameBoard() {
        return gameBoard;
    }
}
