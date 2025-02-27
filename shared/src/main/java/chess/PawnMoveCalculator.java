package chess;

import java.util.Collection;
import java.util.LinkedList;

class PawnMoveCalculator implements PieceMovesCalculator {
    private ChessPiece piece;
    private final Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;
    private ChessMove newMove;
    private boolean moveAdded;

    void addMove(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, ChessGame.TeamColor pieceColor) {
        moveAdded = false;
        piece = board.getPiece(newPosition);
        newMove = new ChessMove(myPosition, newPosition, null);
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            if (piece != null) {
                if (piece.getTeamColor() != pieceColor) {
                    if (newPosition.getRow() == 8) {
                        promotion(myPosition, newPosition);
                    }
                }
                moveAdded = false;
            } else {
                if (newPosition.getRow() == 8) {
                    promotion(myPosition, newPosition);
                } else {
                    moves.add(newMove);
                    moveAdded = true;
                }
            }
        } else {
            if (piece != null) {
                if (piece.getTeamColor() != pieceColor) {
                    if (newPosition.getRow() == 1) {
                        promotion(myPosition, newPosition);
                    }
                }
                moveAdded = false;
            } else {
                if (newPosition.getRow() == 1) {
                    promotion(myPosition, newPosition);
                } else {
                    moves.add(newMove);
                    moveAdded = true;
                }
            }
        }
    }

    void promotion(ChessPosition myPosition, ChessPosition newPosition) {
        newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
        moves.add(newMove);
        newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
        moves.add(newMove);
        newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
        moves.add(newMove);
        newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
        moves.add(newMove);
    }

    void capture(ChessBoard board, ChessPosition myPosition, ChessPosition diagPosition, ChessGame.TeamColor pieceColor, int checkRow) {
        if (board.getPiece(diagPosition) != null) {
            newPosition = diagPosition;
            newMove = new ChessMove(myPosition, newPosition, null);
            piece = board.getPiece(newPosition);
            if (piece.getTeamColor() != pieceColor) {
                if (newPosition.getRow() == checkRow) {
                    promotion(myPosition, newPosition);
                } else {
                    moves.add(newMove);
                }
            }
        }
    }

    void getOneUp(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row + 1, col);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getOneDown(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row - 1, col);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        ChessPosition rightUpDiag = new ChessPosition(row + 1, col + 1);
        ChessPosition leftUpDiag = new ChessPosition(row + 1, col - 1);
        ChessPosition rightDownDiag = new ChessPosition(row - 1, col + 1);
        ChessPosition leftDownDiag = new ChessPosition(row - 1, col - 1);
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            /// first time: move forward two
            if (row == 2) {
                getOneUp(board, myPosition, pieceColor, row, col);
                if (moveAdded) {
                    getOneUp(board, myPosition, pieceColor, row + 1, col);
                }
            } else if (row != 8) { /// move forward one
                getOneUp(board, myPosition, pieceColor, row, col);
            }
            /// capture diagonal
            if (row != 8) {
                if (col != 8) {
                    capture(board, myPosition, rightUpDiag, pieceColor, 8);
                }
                if (col != 1) {
                    capture(board, myPosition, leftUpDiag, pieceColor, 8);
                }
            }
            /// promotion is done in other checks
        } else { /// Black
        /// first time: move forward two
            if (row == 7) {
                getOneDown(board, myPosition, pieceColor, row, col);
                if (moveAdded) {
                    getOneDown(board, myPosition, pieceColor, row - 1, col);
                }
            } else if (row != 1) { /// move forward one
                getOneDown(board, myPosition, pieceColor, row, col);
            }
            /// capture diagonal
            if (row != 1) {
                if (col != 8) {
                    capture(board, myPosition, rightDownDiag, pieceColor, 1);
                }
                if (col != 1) {
                    capture(board, myPosition, leftDownDiag, pieceColor, 1);
                }
            }
            /// promotion is done in other checks
        }
        return moves;
    }
}
