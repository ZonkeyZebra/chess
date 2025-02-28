package chess;

import java.util.Collection;
import java.util.LinkedList;

class KnightMoveCalculator implements PieceMovesCalculator {
    private final Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;

    void addMove(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, ChessGame.TeamColor pieceColor) {
        SharedMoves sharedMoves = new SharedMoves(moves);
        sharedMoves.addMoveKingKnight(board, myPosition, newPosition, pieceColor);
    }

    void getUpRight(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row + 2, col + 1);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getUpLeft(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row + 2, col - 1);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getRightUp(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row + 1, col + 2);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getRightDown(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row - 1, col + 2);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getDownRight(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row - 2, col + 1);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getDownLeft(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row - 2, col - 1);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getLeftUp(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row + 1, col - 2);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getLeftDown(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row - 1, col - 2);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        // edge bottom
        // edge top
        // edge right
        // edge left
        // basic
        if (row == 1) {
            if (col != 8) {
                getUpRight(board, myPosition, pieceColor, row, col);
                getRightUp(board, myPosition, pieceColor, row, col);
            }
            if (col != 1) {
                getUpLeft(board, myPosition, pieceColor, row, col);
                getLeftUp(board, myPosition, pieceColor, row, col);
            }
        } else if (row == 8) {
            if (col != 8) {
                getDownRight(board, myPosition, pieceColor, row, col);
                getRightDown(board, myPosition, pieceColor, row, col);
            }
            if (col != 1) {
                getLeftDown(board, myPosition, pieceColor, row, col);
                getDownLeft(board, myPosition, pieceColor, row, col);
            }
        } else if (col == 1) {
            getRightUp(board, myPosition, pieceColor, row, col);
            getRightDown(board, myPosition, pieceColor, row, col);
            getUpRight(board, myPosition, pieceColor, row, col);
            getDownRight(board, myPosition, pieceColor, row, col);

        } else if (col == 8) {
            getLeftUp(board, myPosition, pieceColor, row, col);
            getLeftDown(board, myPosition, pieceColor, row, col);
            getUpLeft(board, myPosition, pieceColor, row, col);
            getDownLeft(board, myPosition, pieceColor, row, col);

        } else {
            getUpRight(board, myPosition, pieceColor, row, col);
            getUpLeft(board, myPosition, pieceColor, row, col);
            getRightUp(board, myPosition, pieceColor, row, col);
            getRightDown(board, myPosition, pieceColor, row, col);
            getDownRight(board, myPosition, pieceColor, row, col);
            getDownLeft(board, myPosition, pieceColor, row, col);
            getLeftUp(board, myPosition, pieceColor, row, col);
            getLeftDown(board, myPosition, pieceColor, row, col);
        }
        return moves;
    }
}
