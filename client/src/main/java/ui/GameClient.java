package ui;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.GameDAO;
import dataaccess.MySqlGameDAO;
import exception.DataAccessException;
import model.GameData;
import ui.websocket.GameHandler;
import ui.websocket.WebSocketFacade;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class GameClient {
    private final ServerFacade server;
    private final String serverUrl;
    private WebSocketFacade ws;
    private final GameDAO gameDataAccess = new MySqlGameDAO();
    private final GameHandler handler;

    public GameClient(String serverUrl, GameHandler handler) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.handler = handler;
    }

    public String eval(String input, String authToken, ChessGame.TeamColor teamColor, ChessGame chessGame, int gameID, boolean observer) throws Exception {
        String[] tokens = input.split(" ");
        String command = tokens[0];
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
        if (observer) {
            return switch (command) {
                case "redraw" -> redrawChessBoard(teamColor, chessGame);
                case "leave" -> observerLeave(gameID, authToken);
                case "quit" -> "quit";
                default -> observerHelp();
            };
        }
        return switch (command) {
            case "redraw" -> redrawChessBoard(teamColor, chessGame);
            case "leave" -> leave(teamColor, chessGame, gameID, authToken);
            case "move" -> makeMove(params, teamColor, chessGame, authToken);
            case "resign" -> resign(gameID, authToken);
            case "highlight" -> highlightLegalMoves(params, teamColor, chessGame);
            case "quit" -> "quit";
            default -> help();
        };
    }

    private String redrawChessBoard(ChessGame.TeamColor teamColor, ChessGame game) {
        new DrawBoard(teamColor, game.getBoard());
        return "";
    }

    private String leave(ChessGame.TeamColor teamColor, ChessGame game, int gameID, String authToken) throws Exception {
        GameData updateData;
        String blackUsername;
        String whiteUsername;
        if (teamColor == ChessGame.TeamColor.BLACK) {
            blackUsername = "";
            whiteUsername = gameDataAccess.getGame(gameID).whiteUsername();
        } else {
            blackUsername = gameDataAccess.getGame(gameID).blackUsername();
            whiteUsername = "";
        }
        ws = new WebSocketFacade(serverUrl, handler);
        ws.leaveGame(gameID, authToken);
        updateData = new GameData(gameID, whiteUsername, blackUsername, gameDataAccess.getGame(gameID).gameName(), game);
        gameDataAccess.updateGame(updateData);
        return "You left the game.";
    }

    private String observerLeave(int gameID, String authToken) throws Exception {
        ws = new WebSocketFacade(serverUrl, handler);
        ws.leaveGame(gameID, authToken);
        return "You left the game.";
    }

    private String makeMove(String[] params, ChessGame.TeamColor teamColor, ChessGame game, String authToken) throws Exception {
        if (params.length >= 2 && params.length < 4) {
            ChessPosition startPosition = getPositionFromString(params[0], teamColor);
            ChessPosition endPosition = getPositionFromString(params[1], teamColor);
            ChessPiece.PieceType promotion = null;
            if (params.length == 3) {
                promotion = getPieceType(params[2]);
            }
            if (teamColor == game.getTeamTurn()) {
                game.makeMove(new ChessMove(startPosition, endPosition, promotion));
            } else {
                throw new Exception("Not your turn!");
            }
            redrawChessBoard(teamColor, game);
            return "";
        } else {
            return "move <source> <destination> <optional promotion> (e.g. f5 e4 q)";
        }
    }

    private String resign(int gameID, String authToken) throws Exception {
        gameDataAccess.deleteSingleGame(gameID);
        ws = new WebSocketFacade(serverUrl, handler);
        ws.resignGame(gameID, authToken);
        return "You lost!";
    }

    private String highlightLegalMoves(String[] params, ChessGame.TeamColor teamColor, ChessGame game) {
        if (params.length == 1) {
            ChessBoard board = game.getBoard();
            ChessPosition position = getPositionFromString(params[0], teamColor);
            ChessPiece piece = board.getPiece(position);
            Collection<ChessMove> validMoves = piece.pieceMoves(board, position);
            new DrawBoard().getValidMoveBoard(teamColor, board, validMoves);
            if (validMoves.isEmpty()) {
                return "No valid moves for " + piece.getTeamColor() + " " + piece.getPieceType();
            }
            return "";
        } else {
            return "highlight <position> (e.g. f5)";
        }
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

    private String observerHelp() {
        return """
                - redraw
                - leave
                - help
                - quit
                """;
    }

    private ChessPiece.PieceType getPieceType(String input) {
        if (Objects.equals(input, "q")) {
            return ChessPiece.PieceType.QUEEN;
        } else if (Objects.equals(input, "k")) {
            return ChessPiece.PieceType.KNIGHT;
        } else if (Objects.equals(input, "b")) {
            return ChessPiece.PieceType.BISHOP;
        } else if (Objects.equals(input, "r")) {
            return ChessPiece.PieceType.ROOK;
        }
        return null;
    }

    private ChessPosition getPositionFromString(String input, ChessGame.TeamColor teamColor) {
        int row = getRow(input);
        int col = getCol(input);
        return new ChessPosition(row, col);
    }

    private static int getRow(String input) {
        int row = 0;
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
        return row;
    }

    private static int getCol(String input) {
        int col = 0;
        if (input.contains("a")) {
            col = 1;
        }
        if (input.contains("b")) {
            col = 2;
        }
        if (input.contains("c")) {
            col = 3;
        }
        if (input.contains("d")) {
            col = 4;
        }
        if (input.contains("e")) {
            col = 5;
        }
        if (input.contains("f")) {
            col = 6;
        }
        if (input.contains("g")) {
            col = 7;
        }
        if (input.contains("h")) {
            col = 8;
        }
        return col;
    }

}
