package ui;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ui.websocket.WebSocketFacade;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class GameClient {
    private final ServerFacade server;
    private final String serverUrl;
    private WebSocketFacade ws;

    public GameClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input, String authToken, ChessGame.TeamColor teamColor, ChessBoard board) {
        String[] tokens = input.split(" ");
        String command = tokens[0];
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (command) {
            case "redraw" -> redrawChessBoard(teamColor, board);
            case "leave" -> leave();
            case "move" -> makeMove(params);
            case "resign" -> resign();
            case "highlight" -> highlightLegalMoves(params, teamColor, board);
            case "quit" -> "quit";
            default -> help();
        };
    }

    private String redrawChessBoard(ChessGame.TeamColor teamColor, ChessBoard board) {
        new DrawBoard(teamColor, board);
        return "";
    }

    private String leave() {
        return "You left the game.";
    }

    private String makeMove(String[] params) {
        return "TODO: move";
    }

    private String resign() {
        return "You lost!";
    }

    private String highlightLegalMoves(String[] params, ChessGame.TeamColor teamColor, ChessBoard board) {
        ChessPosition position = getPositionFromString(params[0], teamColor);
        ChessPiece piece = board.getPiece(position);
        Collection<ChessMove> validMoves = piece.pieceMoves(board, position);
        new DrawBoard().getValidMoveBoard(teamColor, board, validMoves);
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

    private ChessPosition getPositionFromString(String input, ChessGame.TeamColor teamColor) {
        int row = 0;
        int col = 0;
        if (input.contains("a")) {
            if (teamColor == ChessGame.TeamColor.WHITE) {
                col = 1;
            } else {
                col = 8;
            }
        }
        if (input.contains("b")) {
            if (teamColor == ChessGame.TeamColor.WHITE) {
                col = 2;
            } else {
                col = 7;
            }
        }
        if (input.contains("c")) {
            if (teamColor == ChessGame.TeamColor.WHITE) {
                col = 3;
            } else {
                col = 6;
            }
        }
        if (input.contains("d")) {
            if (teamColor == ChessGame.TeamColor.WHITE) {
                col = 4;
            } else {
                col = 5;
            }
        }
        if (input.contains("e")) {
            if (teamColor == ChessGame.TeamColor.WHITE) {
                col = 5;
            } else {
                col = 4;
            }
        }
        if (input.contains("f")) {
            if (teamColor == ChessGame.TeamColor.WHITE) {
                col = 6;
            } else {
                col = 3;
            }
        }
        if (input.contains("g")) {
            if (teamColor == ChessGame.TeamColor.WHITE) {
                col = 7;
            } else {
                col = 2;
            }
        }
        if (input.contains("h")) {
            if (teamColor == ChessGame.TeamColor.WHITE) {
                col = 8;
            } else {
                col = 1;
            }
        }

        if (input.contains("1")) {
            row = 1;
        }
        if (input.contains("2")) {
            row = 2;
        }
        if (input.contains("3")) {
            row = 3;
        }
        if (input.contains("4")) {
            row = 4;
        }
        if (input.contains("5")) {
            row = 5;
        }
        if (input.contains("6")) {
            row = 6;
        }
        if (input.contains("7")) {
            row = 7;
        }
        if (input.contains("8")) {
            row = 8;
        }

        return new ChessPosition(row, col);
    }
}
