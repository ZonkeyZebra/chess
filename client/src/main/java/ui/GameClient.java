package ui;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ui.websocket.WebSocketFacade;

import java.util.Arrays;
import java.util.Objects;

public class GameClient {
    private final ServerFacade server;
    private final String serverUrl;
    private WebSocketFacade ws;
    private ChessGame.TeamColor teamColor;

    public GameClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input, String authToken) {
        String[] tokens = input.split(" ");
        String command = tokens[0];
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (command) {
            case "redraw" -> redrawChessBoard(params);
            case "leave" -> leave();
            case "move" -> makeMove(params);
            case "resign" -> resign();
            case "highlight" -> highlightLegalMoves(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    private String redrawChessBoard(String[] params) {
        teamColor = getTeamColor(params[0]);
        ChessBoard board = new Gson().fromJson(params[1], ChessBoard.class);
        new DrawBoard(teamColor, board);
        return "TODO: redrawChessBoard";
    }

    private String leave() {
        return "TODO: leave";
    }

    private String makeMove(String[] params) {
        return "TODO: move";
    }

    private String resign() {
        return "TODO: resign";
    }

    private String highlightLegalMoves(String[] params) {
        return "TODO: highlightLegalMoves";
    }

    private String help() {
        return """
                - redraw
                - leave
                - move <source> <destination> <optional promotion> (e.g. f5 e4 q)
                - resign
                - highlight <position> (e.g. f5)
                - help
                - quit
                """;
    }

    private ChessGame.TeamColor getTeamColor(String color) {
        teamColor = ChessGame.TeamColor.WHITE;
        if (Objects.equals(color, "black")) {
            teamColor = ChessGame.TeamColor.BLACK;
        }
        return teamColor;
    }
}
