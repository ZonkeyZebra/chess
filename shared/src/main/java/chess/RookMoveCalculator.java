package chess;

import java.util.Collection;
import java.util.LinkedList;

class RookMoveCalculator implements PieceMovesCalculator {
    private final Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;
    private boolean hitOtherPiece = false;

    void addMove(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, ChessGame.TeamColor pieceColor) {
        ChessPiece piece = board.getPiece(newPosition);
        hitOtherPiece = false;
        ChessMove newMove = new ChessMove(myPosition, newPosition, null);
        if (piece != null) {
            hitOtherPiece = true;
            if (piece.getTeamColor() != pieceColor) {
                moves.add(newMove);
            }
        } else {
            moves.add(newMove);
        }
    }

    void getUp(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int next, int row, int col) {
        do {
            newPosition = new ChessPosition(row + next, col);
            addMove(board, myPosition, newPosition, pieceColor);
            next++;
        } while(newPosition.getRow() != 8 && !hitOtherPiece);
    }

    void getDown(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int next, int row, int col) {
        do {
            newPosition = new ChessPosition(row - next, col);
            addMove(board, myPosition, newPosition, pieceColor);
            next++;
        } while(newPosition.getRow() != 1 && !hitOtherPiece);
    }

    void getRight(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int next, int row, int col) {
        do {
            newPosition = new ChessPosition(row, col + next);
            addMove(board, myPosition, newPosition, pieceColor);
            next++;
        } while(newPosition.getColumn() != 8 && !hitOtherPiece);
    }

    void getLeft(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int next, int row, int col) {
        do {
            newPosition = new ChessPosition(row, col - next);
            addMove(board, myPosition, newPosition, pieceColor);
            next++;
        } while(newPosition.getColumn() != 1 && !hitOtherPiece);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        // up
        if (row != 8) {
            getUp(board, myPosition, pieceColor, 1, row, col);
        }
        // right
        if (col != 8) {
            getRight(board, myPosition, pieceColor, 1, row, col);
        }
        // down
        if (row != 1) {
            getDown(board, myPosition, pieceColor, 1, row, col);
        }
        // left
        if (col != 1) {
            getLeft(board, myPosition, pieceColor, 1, row, col);
        }
        return moves;
    }
}
