package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import static ui.EscapeSequences.*;

/// This class prints the board to the console
public class DrawBoard {
    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String[] WHITE_HEADERS = { "a", "b", "c", "d", "e", "f", "g", "h" };
    private static final String[] BLACK_HEADERS = { "h", "g", "f", "e", "d", "c", "b", "a" };
    private static final Collection<ChessMove> EMPTY_MOVES = new ArrayList<>();

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        ChessBoard board = new ChessBoard();
        board.resetBoard();

        // Draw normal white and black board
        drawHeaders(out, WHITE_HEADERS);
        drawBoard(out, ChessGame.TeamColor.WHITE, board, EMPTY_MOVES);
        drawHeaders(out, WHITE_HEADERS);

        drawHeaders(out, BLACK_HEADERS);
        drawBoard(out, ChessGame.TeamColor.BLACK, board, EMPTY_MOVES);
        drawHeaders(out, BLACK_HEADERS);

        // Draw valid moves board
        ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        Collection<ChessMove> testMoves = piece.pieceMoves(board, new ChessPosition(7,7));
        drawHeaders(out, BLACK_HEADERS);
        drawBoard(out, ChessGame.TeamColor.BLACK, board, testMoves);
        drawHeaders(out, BLACK_HEADERS);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    DrawBoard () {

    }

    DrawBoard (ChessGame.TeamColor teamColor, ChessBoard board) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        if (teamColor == ChessGame.TeamColor.BLACK) {
            drawHeaders(out, BLACK_HEADERS);
            drawBoard(out, teamColor, board, EMPTY_MOVES);
            drawHeaders(out, BLACK_HEADERS);
        } else {
            drawHeaders(out, WHITE_HEADERS);
            drawBoard(out, teamColor, board, EMPTY_MOVES);
            drawHeaders(out, WHITE_HEADERS);
        }
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    public void getValidMoveBoard(ChessGame.TeamColor teamColor, ChessBoard board, Collection<ChessMove> moves) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        if (teamColor == ChessGame.TeamColor.BLACK) {
            drawHeaders(out, BLACK_HEADERS);
            drawBoard(out, teamColor, board, moves);
            drawHeaders(out, BLACK_HEADERS);
        } else {
            drawHeaders(out, WHITE_HEADERS);
            drawBoard(out, teamColor, board, moves);
            drawHeaders(out, WHITE_HEADERS);
        }
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out, String[] headers) {

        setBlack(out);

        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            if (boardCol == 3 || boardCol == 5) {
                out.print("  ");
            } else {
                out.print("   ");
            }
            printHeaderText(out, headers[boardCol]);
        }

        out.println();
    }

    private static void printHeaderText(PrintStream out, String text) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(text);

        setBlack(out);
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setDarkGray(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }

    private static void setLightGray(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }

    private static void setLightGreen(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREEN);
        out.print(SET_TEXT_COLOR_LIGHT_GREEN);
    }

    private static void printPiece(PrintStream out, String piece, String background, String textColor) {
        out.print(background);
        out.print(textColor);

        out.print(piece);

        setWhite(out);
    }

    private static boolean isEvenNum(double num) {
        return num == 0 || num == 2 || num == 4 || num == 6 || num == 8;
    }

    private static void drawBoard(PrintStream out, ChessGame.TeamColor teamColor, ChessBoard board, Collection<ChessMove> moves) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            reverseBoard(out, teamColor, board, moves);
        } else {
            drawNormalBoard(out, teamColor, board, moves);
        }
    }

    private static void reverseBoard(PrintStream out, ChessGame.TeamColor teamColor, ChessBoard board, Collection<ChessMove> moves) {
        for (int row = BOARD_SIZE_IN_SQUARES - 1; row >= 0; row--) {
            getCols(out, board, row, true, moves);
        }
    }

    private static void drawNormalBoard(PrintStream out, ChessGame.TeamColor teamColor, ChessBoard board, Collection<ChessMove> moves) {
        for (int row = 0; row < BOARD_SIZE_IN_SQUARES; row++) {
            getCols(out, board, row, false, moves);
        }
    }

    private static void getCols(PrintStream out, ChessBoard board, int row, boolean reverse, Collection<ChessMove> moves) {
        printHeaderText(out, String.format((row + 1) + " "));
        if (reverse) {
            for (int col = 0; col < BOARD_SIZE_IN_SQUARES; col++) {
                if (moves.isEmpty()) {
                    squareCheck(out, board, row, col, moves);
                } else {
                    validSquareCheck(out, board, row, col, moves);
                }
            }
        } else {
            for (int col = BOARD_SIZE_IN_SQUARES - 1; col >= 0; col--) {
                if (moves.isEmpty()) {
                    squareCheck(out, board, row, col, moves);
                } else {
                    validSquareCheck(out, board, row, col, moves);
                }
            }
        }
        printHeaderText(out, String.format(" " + (row + 1)));
        out.println();
    }

    private static void validSquareCheck(PrintStream out, ChessBoard board, int row, int col, Collection<ChessMove> moves) {
        boolean wasValid = false;
        for (ChessMove move : moves) {
            ChessPosition position = move.getEndPosition();
            if ((row + 1) == position.getRow() && (col + 1) == position.getColumn()) {
                setLightGreen(out);
                printSquare(out, board, row, col, SET_BG_COLOR_LIGHT_GREEN);
                wasValid = true;
            }
        }
        if (!wasValid) {
            squareCheck(out, board, row, col, moves);
        }
    }

    private static void squareCheck(PrintStream out, ChessBoard board, int row, int col, Collection<ChessMove> moves) {
        if (isEvenNum(row)) {
            if (isEvenNum(col)) {
                setLightGray(out);
                printSquare(out, board, row, col, SET_BG_COLOR_LIGHT_GREY);
            } else {
                setDarkGray(out);
                printSquare(out, board, row, col, SET_BG_COLOR_DARK_GREY);
            }
        } else {
            if (isEvenNum(col)) {
                setDarkGray(out);
                printSquare(out, board, row, col, SET_BG_COLOR_DARK_GREY);
            } else {
                setLightGray(out);
                printSquare(out, board, row, col, SET_BG_COLOR_LIGHT_GREY);
            }
        }
    }

    private static void printSquare(PrintStream out, ChessBoard board, int row, int col, String setBgColorWhite) {
        ChessPiece piece = board.getPiece(new ChessPosition(row+1, col+1));
        if (piece == null) {
            out.print(EMPTY.repeat(1));
        } else {
            String pieceString = getPieceString(piece.getPieceType(), piece.getTeamColor());
            printPiece(out, pieceString, setBgColorWhite, getPieceTextColor(piece.getTeamColor()));
        }
        setBlack(out);
    }

    private static String getPieceString(ChessPiece.PieceType type, ChessGame.TeamColor color) {
        String pieceString = null;
        if (type == ChessPiece.PieceType.PAWN) {
            if (color == ChessGame.TeamColor.BLACK) {
                pieceString = BLACK_PAWN;
            } else {
                pieceString = WHITE_PAWN;
            }
        } else if (type == ChessPiece.PieceType.ROOK) {
            if (color == ChessGame.TeamColor.BLACK) {
                pieceString = BLACK_ROOK;
            } else {
                pieceString = WHITE_ROOK;
            }
        } else if (type == ChessPiece.PieceType.KNIGHT) {
            if (color == ChessGame.TeamColor.BLACK) {
                pieceString = BLACK_KNIGHT;
            } else {
                pieceString = WHITE_KNIGHT;
            }
        } else if (type == ChessPiece.PieceType.BISHOP) {
            if (color == ChessGame.TeamColor.BLACK) {
                pieceString = BLACK_BISHOP;
            } else {
                pieceString = WHITE_BISHOP;
            }
        } else if (type == ChessPiece.PieceType.QUEEN) {
            if (color == ChessGame.TeamColor.BLACK) {
                pieceString = BLACK_QUEEN;
            } else {
                pieceString = WHITE_QUEEN;
            }
        } else if (type == ChessPiece.PieceType.KING) {
            if (color == ChessGame.TeamColor.BLACK) {
                pieceString = BLACK_KING;
            } else {
                pieceString = WHITE_KING;
            }
        }
        return pieceString;
    }

    private static String getPieceTextColor(ChessGame.TeamColor teamColor) {
        String color;
        if (teamColor == ChessGame.TeamColor.BLACK) {
            color = SET_TEXT_COLOR_BLUE;
        } else {
            color = SET_TEXT_COLOR_WHITE;
        }
        return color;
    }
}
