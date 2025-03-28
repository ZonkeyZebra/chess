package ui;

import ui.websocket.WebSocketFacade;

import java.util.Arrays;

public class GameClient {
    private final ServerFacade server;
    private final String serverUrl;
    private WebSocketFacade ws;

    public GameClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        String[] tokens = input.split(" ");
        String command = tokens[0];
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (command) {
            case "redraw" -> redrawChessBoard();
            case "leave" -> leave();
            case "makeMove" -> makeMove(params);
            case "resign" -> resign();
            case "highlight" -> highlightLegalMoves(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    private String redrawChessBoard() {
        return "TODO: redrawChessBoard";
    }

    private String leave() {
        return "TODO: leave";
    }

    private String makeMove(String[] params) {
        return "TODO: makeMove";
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
                - makeMove
                - resign
                - highlight
                - help
                - quit
                """;
    }
}
