package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static ui.EscapeSequences.*;

/// This class prints the board to the console
public class DrawBoard {
    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 1;

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);

        drawChessBoard(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {

        setBlack(out);

        String[] headers = { "a", "b", "c", "d", "e", "f", "g", "h" };
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);

            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print(EMPTY.repeat(1));
            }
        }

        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = 1;
        int suffixLength = 0;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String text) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(text);

        setBlack(out);
    }

    private static void drawChessBoard(PrintStream out) {

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            String evenOdd = "odd";
            if (isEvenNum(boardRow)) {
                evenOdd = "even";
            }

            drawRowOfSquares(out, evenOdd, boardRow);

        }
    }

    private static void drawRowOfSquares(PrintStream out, String evenOdd, int boardRow) {

        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            if (Objects.equals(evenOdd, "even")) {
                printEvenRow(out, boardCol, boardRow);
            } else {
                printOddRow(out, boardCol, boardRow);
            }
        }

        out.println();
    }

    private static void printEvenRow(PrintStream out, int boardCol, int boardRow) {
        setLightGreen(out);
        int prefixLength = 0;
        int suffixLength = 0;

        if (isEvenNum(boardCol)) {
            setLightPurple(out);
            if (boardRow == 0) {
                if (boardCol == 0) {
                    printPiece(out, BLACK_ROOK, SET_BG_COLOR_LIGHT_PURPLE, SET_TEXT_COLOR_BLACK);
                } else if (boardCol == 2) {
                    printPiece(out, BLACK_BISHOP, SET_BG_COLOR_LIGHT_PURPLE, SET_TEXT_COLOR_BLACK);
                } else if (boardCol == 4) {
                    printPiece(out, BLACK_KING, SET_BG_COLOR_LIGHT_PURPLE, SET_TEXT_COLOR_BLACK);
                } else if (boardCol == 6) {
                    printPiece(out, BLACK_KNIGHT, SET_BG_COLOR_LIGHT_PURPLE, SET_TEXT_COLOR_BLACK);
                }
            } else if (boardRow == 6) {
                printPiece(out, WHITE_PAWN, SET_BG_COLOR_LIGHT_PURPLE, SET_TEXT_COLOR_WHITE);
            } else {
                out.print(EMPTY.repeat(1));
            }
        } else {
            if (boardRow == 0) {
                if (boardCol == 7) {
                    printPiece(out, BLACK_ROOK, SET_BG_COLOR_LIGHT_GREEN, SET_TEXT_COLOR_BLACK);
                } else if (boardCol == 5) {
                    printPiece(out, BLACK_BISHOP, SET_BG_COLOR_LIGHT_GREEN, SET_TEXT_COLOR_BLACK);
                } else if (boardCol == 3) {
                    printPiece(out, BLACK_QUEEN, SET_BG_COLOR_LIGHT_GREEN, SET_TEXT_COLOR_BLACK);
                } else if (boardCol == 1) {
                    printPiece(out, BLACK_KNIGHT, SET_BG_COLOR_LIGHT_GREEN, SET_TEXT_COLOR_BLACK);
                }
            } else if (boardRow == 6) {
                printPiece(out, WHITE_PAWN, SET_BG_COLOR_LIGHT_GREEN, SET_TEXT_COLOR_WHITE);
            } else {
                out.print(EMPTY.repeat(1));
            }
        }

        setBlack(out);
    }

    private static void printOddRow(PrintStream out, int boardCol, int boardRow) {
        setLightPurple(out);
        int prefixLength = 0;
        int suffixLength = 0;

        if (isEvenNum(boardCol)) {
            setLightGreen(out);
            if (boardRow == 1) {
                printPiece(out, BLACK_PAWN, SET_BG_COLOR_LIGHT_GREEN, SET_TEXT_COLOR_BLACK);
            } else if (boardRow == 7) {
                if (boardCol == 0) {
                    printPiece(out, WHITE_ROOK, SET_BG_COLOR_LIGHT_GREEN, SET_TEXT_COLOR_WHITE);
                } else if (boardCol == 2) {
                    printPiece(out, WHITE_BISHOP, SET_BG_COLOR_LIGHT_GREEN, SET_TEXT_COLOR_WHITE);
                } else if (boardCol == 4) {
                    printPiece(out, WHITE_KING, SET_BG_COLOR_LIGHT_GREEN, SET_TEXT_COLOR_WHITE);
                } else if (boardCol == 6) {
                    printPiece(out, WHITE_KNIGHT, SET_BG_COLOR_LIGHT_GREEN, SET_TEXT_COLOR_WHITE);
                }
            } else {
                out.print(EMPTY.repeat(1));
            }
        } else {
            if (boardRow == 1) {
                printPiece(out, BLACK_PAWN, SET_BG_COLOR_LIGHT_PURPLE, SET_TEXT_COLOR_BLACK);
            } else if (boardRow == 7) {
                if (boardCol == 7) {
                    printPiece(out, WHITE_ROOK, SET_BG_COLOR_LIGHT_PURPLE, SET_TEXT_COLOR_WHITE);
                } else if (boardCol == 5) {
                    printPiece(out, WHITE_BISHOP, SET_BG_COLOR_LIGHT_PURPLE, SET_TEXT_COLOR_WHITE);
                } else if (boardCol == 3) {
                    printPiece(out, WHITE_QUEEN, SET_BG_COLOR_LIGHT_PURPLE, SET_TEXT_COLOR_WHITE);
                } else if (boardCol == 1) {
                    printPiece(out, WHITE_KNIGHT, SET_BG_COLOR_LIGHT_PURPLE, SET_TEXT_COLOR_WHITE);
                }
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

    private static void setGray(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
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
