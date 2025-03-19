package ui;

import chess.ChessGame;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static ui.EscapeSequences.*;

/// This class prints the board to the console
public class DrawBoard {
    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String[] whiteHeaders = { "a", "b", "c", "d", "e", "f", "g", "h" };
    private static final String[] blackHeaders = { "h", "g", "f", "e", "d", "c", "b", "a" };

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessGame.TeamColor teamColor = ChessGame.TeamColor.WHITE;

        out.print(ERASE_SCREEN);

        // draw board from white perspective
        drawHeaders(out, whiteHeaders);
        drawChessBoard(out, teamColor);
        drawHeaders(out, whiteHeaders);

        teamColor = ChessGame.TeamColor.BLACK;
        drawHeaders(out, blackHeaders);
        drawChessBoard(out, teamColor);
        drawHeaders(out, blackHeaders);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    DrawBoard (ChessGame.TeamColor teamColor) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        if (teamColor == ChessGame.TeamColor.BLACK) {
            drawHeaders(out, blackHeaders);
            drawChessBoard(out, teamColor);
            drawHeaders(out, blackHeaders);
        } else {
            drawHeaders(out, whiteHeaders);
            drawChessBoard(out, teamColor);
            drawHeaders(out, whiteHeaders);
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

    private static void drawChessBoard(PrintStream out, ChessGame.TeamColor teamColor) {

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            if (teamColor == ChessGame.TeamColor.WHITE) {
                printHeaderText(out, (8 - boardRow) + " ");
            } else {
                printHeaderText(out, String.format((boardRow + 1) + " "));
            }
            String evenOdd = "odd";
            if (isEvenNum(boardRow)) {
                evenOdd = "even";
            }

            drawRowOfSquares(out, evenOdd, boardRow, teamColor);

        }
    }

    private static void drawRowOfSquares(PrintStream out, String evenOdd, int boardRow, ChessGame.TeamColor teamColor) {
        String setTextColorEven = SET_TEXT_COLOR_WHITE;
        String setTextColorOdd = SET_TEXT_COLOR_BLACK;
        String rookEven = WHITE_ROOK;
        String rookOdd = BLACK_ROOK;
        String bishopEven = WHITE_BISHOP;
        String bishopOdd = BLACK_BISHOP;
        String queenEven = WHITE_QUEEN;
        String queenOdd = BLACK_QUEEN;
        String kingEven = WHITE_KING;
        String kingOdd = BLACK_KING;
        String knightEven = WHITE_KNIGHT;
        String knightOdd = BLACK_KNIGHT;
        String pawnEven = BLACK_PAWN;
        String pawnOdd = WHITE_PAWN;
        String pawnColorEven = SET_TEXT_COLOR_BLACK;
        String pawnColorOdd = SET_TEXT_COLOR_WHITE;
        if (teamColor == ChessGame.TeamColor.WHITE) {
            rookEven = BLACK_ROOK;
            setTextColorEven = SET_TEXT_COLOR_BLACK;
            bishopEven = BLACK_BISHOP;
            queenEven = BLACK_QUEEN;
            knightEven = BLACK_KNIGHT;
            kingEven = BLACK_KING;
            rookOdd = WHITE_ROOK;
            setTextColorOdd = SET_TEXT_COLOR_WHITE;
            bishopOdd = WHITE_BISHOP;
            queenOdd = WHITE_QUEEN;
            kingOdd = WHITE_KING;
            knightOdd = WHITE_KNIGHT;
            pawnEven = WHITE_PAWN;
            pawnOdd = BLACK_PAWN;
            pawnColorEven = SET_TEXT_COLOR_WHITE;
            pawnColorOdd = SET_TEXT_COLOR_BLACK;
        }

        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            if (Objects.equals(evenOdd, "even")) {
                printEvenRow(out, boardCol, boardRow, setTextColorEven, rookEven, bishopEven, queenEven, knightEven, kingEven, pawnEven, pawnColorEven);
            } else {
                printOddRow(out, boardCol, boardRow, setTextColorOdd, rookOdd, bishopOdd, queenOdd, knightOdd, kingOdd, pawnOdd, pawnColorOdd);
            }
        }

        if (teamColor == ChessGame.TeamColor.WHITE) {
            printHeaderText(out, " " + (8 - boardRow));
        } else {
            printHeaderText(out, String.format(" " + (boardRow + 1)));
        }
        out.println();
    }

    private static void printEvenRow(PrintStream out, int boardCol, int boardRow, String setTextColor, String rook,
                                     String bishop, String queen, String knight, String king, String pawn, String pawnColor) {
        setLightGreen(out);

        if (isEvenNum(boardCol)) {
            setLightPurple(out);
            if (boardRow == 0) {
                getEvenPieces(out, boardCol, rook, SET_BG_COLOR_LIGHT_PURPLE, setTextColor, bishop, king, knight);
            } else if (boardRow == 6) {
                printPiece(out, pawn, SET_BG_COLOR_LIGHT_PURPLE, pawnColor);
            } else {
                out.print(EMPTY.repeat(1));
            }
        } else {
            if (boardRow == 0) {
                getOddPieces(out, boardCol, rook, SET_BG_COLOR_LIGHT_GREEN, setTextColor, bishop, queen, knight);
            } else if (boardRow == 6) {
                printPiece(out, pawn, SET_BG_COLOR_LIGHT_GREEN, pawnColor);
            } else {
                out.print(EMPTY.repeat(1));
            }
        }

        setBlack(out);
    }

    private static void getOddPieces(PrintStream out, int boardCol, String rook, String setBgColor, String setTextColor, String bishop, String queen, String knight) {
        if (boardCol == 7) {
            printPiece(out, rook, setBgColor, setTextColor);
        } else if (boardCol == 5) {
            printPiece(out, bishop, setBgColor, setTextColor);
        } else if (boardCol == 3) {
            printPiece(out, queen, setBgColor, setTextColor);
        } else if (boardCol == 1) {
            printPiece(out, knight, setBgColor, setTextColor);
        }
    }

    private static void getEvenPieces(PrintStream out, int boardCol, String rook, String setBgColor, String setTextColor, String bishop, String king, String knight) {
        if (boardCol == 0) {
            printPiece(out, rook, setBgColor, setTextColor);
        } else if (boardCol == 2) {
            printPiece(out, bishop, setBgColor, setTextColor);
        } else if (boardCol == 4) {
            printPiece(out, king, setBgColor, setTextColor);
        } else if (boardCol == 6) {
            printPiece(out, knight, setBgColor, setTextColor);
        }
    }

    private static void printOddRow(PrintStream out, int boardCol, int boardRow, String setTextColor, String rook,
                                    String bishop, String queen, String knight, String king, String pawn, String pawnColor) {
        setLightPurple(out);

        if (isEvenNum(boardCol)) {
            setLightGreen(out);
            if (boardRow == 1) {
                printPiece(out, pawn, SET_BG_COLOR_LIGHT_GREEN, pawnColor);
            } else if (boardRow == 7) {
                getEvenPieces(out, boardCol, rook, SET_BG_COLOR_LIGHT_GREEN, setTextColor, bishop, king, knight);
            } else {
                out.print(EMPTY.repeat(1));
            }
        } else {
            if (boardRow == 1) {
                printPiece(out, pawn, SET_BG_COLOR_LIGHT_PURPLE, pawnColor);
            } else if (boardRow == 7) {
                getOddPieces(out, boardCol, rook, SET_BG_COLOR_LIGHT_PURPLE, setTextColor, bishop, queen, knight);
            } else {
                out.print(EMPTY.repeat(1));
            }
        }

        setBlack(out);
    }

    private static void setLightPurple(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_PURPLE);
        out.print(SET_TEXT_COLOR_LIGHT_PURPLE);
    }

    private static void setLightGreen(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREEN);
        out.print(SET_TEXT_COLOR_LIGHT_GREEN);
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setLightGray(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }

    private static void setDarkGray(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPiece(PrintStream out, String piece, String background, String textColor) {
        out.print(background);
        out.print(textColor);

        out.print(piece);

        setWhite(out);
    }

    private static boolean isEvenNum(double num) {
        if (num == 0 || num == 2 || num == 4 || num == 6 || num == 8) {
            return true;
        }
        return false;
    }

}
