package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static ui.EscapeSequences.*;

/// This class prints the board to the console
public class DrawBoard {
    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String[] WHITE_HEADERS = { "a", "b", "c", "d", "e", "f", "g", "h" };
    private static final String[] BLACK_HEADERS = { "h", "g", "f", "e", "d", "c", "b", "a" };

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessGame.TeamColor teamColor = ChessGame.TeamColor.WHITE;

        out.print(ERASE_SCREEN);

        // draw board from white perspective
        drawHeaders(out, WHITE_HEADERS);
        drawChessBoard(out, teamColor);
        drawHeaders(out, WHITE_HEADERS);

        teamColor = ChessGame.TeamColor.BLACK;
        drawHeaders(out, BLACK_HEADERS);
        drawChessBoard(out, teamColor);
        drawHeaders(out, BLACK_HEADERS);

        ChessBoard board = new ChessBoard();
        board.resetWhiteBoard();
        drawHeaders(out, WHITE_HEADERS);
        drawBoard(out, ChessGame.TeamColor.WHITE, board);
        drawHeaders(out, WHITE_HEADERS);

        board.resetBoard();
        drawHeaders(out, BLACK_HEADERS);
        drawBoard(out, ChessGame.TeamColor.BLACK, board);
        drawHeaders(out, BLACK_HEADERS);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    DrawBoard (ChessGame.TeamColor teamColor, ChessBoard board) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        if (teamColor == ChessGame.TeamColor.BLACK) {
            drawHeaders(out, BLACK_HEADERS);
            drawChessBoard(out, teamColor);
            drawHeaders(out, BLACK_HEADERS);
        } else {
            drawHeaders(out, WHITE_HEADERS);
            drawChessBoard(out, teamColor);
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
            if (teamColor == ChessGame.TeamColor.WHITE) {
                if (Objects.equals(evenOdd, "even")) {
                    printEvenRow(out, boardCol, boardRow, setTextColorEven, rookEven, bishopEven, queenEven,
                            knightEven, kingEven, pawnEven, pawnColorEven);
                } else {
                    printOddRow(out, boardCol, boardRow, setTextColorOdd, rookOdd, bishopOdd, queenOdd,
                            knightOdd, kingOdd, pawnOdd, pawnColorOdd);
                }
            } else {
                if (Objects.equals(evenOdd, "even")) {
                    printEvenRow(out, boardCol, boardRow, setTextColorEven, rookEven, bishopEven, kingEven,
                            knightEven, queenEven, pawnEven, pawnColorEven);
                } else {
                    printOddRow(out, boardCol, boardRow, setTextColorOdd, rookOdd, bishopOdd, kingOdd,
                            knightOdd, queenOdd, pawnOdd, pawnColorOdd);
                }
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

    private static void getOddPieces(PrintStream out, int boardCol, String rook, String setBgColor, String setTextColor,
                                     String bishop, String queen, String knight) {
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

    private static void getEvenPieces(PrintStream out, int boardCol, String rook, String setBgColor, String setTextColor,
                                      String bishop, String king, String knight) {
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

    private static void drawBoard(PrintStream out, ChessGame.TeamColor teamColor, ChessBoard board) {
        for (int row = 0; row < BOARD_SIZE_IN_SQUARES; row++) {
            if (teamColor == ChessGame.TeamColor.WHITE) {
                printHeaderText(out, (8 - row) + " ");
            } else {
                printHeaderText(out, String.format((row + 1) + " "));
            }
            for (int col = 0; col < BOARD_SIZE_IN_SQUARES; col++) {
                if (isEvenNum(row)) {
                    if (isEvenNum(col)) {
                        setLightGray(out);
                        printSquare(out, teamColor, board, row, col, SET_BG_COLOR_LIGHT_GREY);
                    } else {
                        setDarkGray(out);
                        printSquare(out, teamColor, board, row, col, SET_BG_COLOR_DARK_GREY);
                    }
                } else {
                    if (isEvenNum(col)) {
                        setDarkGray(out);
                        printSquare(out, teamColor, board, row, col, SET_BG_COLOR_DARK_GREY);
                    } else {
                        setLightGray(out);
                        printSquare(out, teamColor, board, row, col, SET_BG_COLOR_LIGHT_GREY);
                    }
                }
            }
            if (teamColor == ChessGame.TeamColor.WHITE) {
                printHeaderText(out, " " + (8 - row));
            } else {
                printHeaderText(out, String.format(" " + (row + 1)));
            }
            out.println();
        }
    }

    private static void printSquare(PrintStream out, ChessGame.TeamColor teamColor, ChessBoard board, int row, int col, String setBgColorWhite) {
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
        String color = null;
        if (teamColor == ChessGame.TeamColor.BLACK) {
            color = SET_TEXT_COLOR_BLUE;
        } else {
            color = SET_TEXT_COLOR_WHITE;
        }
        return color;
    }
}
