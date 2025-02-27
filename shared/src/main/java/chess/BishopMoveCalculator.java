package chess;

import java.util.Collection;
import java.util.LinkedList;

class BishopMoveCalculator implements PieceMovesCalculator {
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

    void getRightUp(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int next, int row, int col) {
        do {
            newPosition = new ChessPosition(row + next, col + next);
            addMove(board, myPosition, newPosition, pieceColor);
            next++;
        } while(newPosition.getRow() != 8 && newPosition.getColumn() != 8 && !hitOtherPiece);
    }

    void getRightDown(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int next, int row, int col) {
        do {
            newPosition = new ChessPosition(row - next, col + next);
            addMove(board, myPosition, newPosition, pieceColor);
            next++;
        } while(newPosition.getRow() != 1 && newPosition.getColumn() != 8 && !hitOtherPiece);
    }

    void getLeftUp(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int next, int row, int col) {
        do {
            newPosition = new ChessPosition(row + next, col - next);
            addMove(board, myPosition, newPosition, pieceColor);
            next++;
        } while(newPosition.getColumn() != 1 && newPosition.getRow() != 8 && !hitOtherPiece);
    }

    void getLeftDown(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int next, int row, int col) {
        do {
            newPosition = new ChessPosition(row - next, col - next);
            addMove(board, myPosition, newPosition, pieceColor);
            next++;
        } while(newPosition.getColumn() != 1 && newPosition.getRow() != 1 && !hitOtherPiece);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        // right top
        if (row != 8 && col != 8) {
            getRightUp(board, myPosition, pieceColor, 1, row, col);
        }
        // left top
        if (row != 8 && col != 1) {
            getLeftUp(board, myPosition, pieceColor, 1, row, col);
        }
        // right bottom
        if (row != 1 && col != 8) {
            getRightDown(board, myPosition, pieceColor, 1, row, col);
        }
        // left bottom
        if (row != 1 && col != 1) {
            getLeftDown(board, myPosition, pieceColor, 1, row, col);
        }
        return moves;
    }
}
