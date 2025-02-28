package chess;

import java.util.Collection;
import java.util.LinkedList;

class KingMoveCalculator implements PieceMovesCalculator {
    private final Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;

    void addMove(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, ChessGame.TeamColor pieceColor) {
        SharedMoves sharedMoves = new SharedMoves(moves);
        sharedMoves.addMoveKingKnight(board, myPosition, newPosition, pieceColor);
    }

    void getOneUp(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row + 1, col);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getOneDown(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row - 1, col);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getOneRight(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row, col + 1);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getOneLeft(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row, col - 1);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getOneRightUp(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row + 1, col + 1);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getOneRightDown(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row - 1, col + 1);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getOneLeftUp(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row + 1, col - 1);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    void getOneLeftDown(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int row, int col) {
        newPosition = new ChessPosition(row - 1, col - 1);
        addMove(board, myPosition, newPosition, pieceColor);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        // up
        if (row != 8) {
            getOneUp(board, myPosition, pieceColor, row, col);
        }
        // right up
        if (row != 8 && col != 8) {
            getOneRightUp(board, myPosition, pieceColor, row, col);
        }
        // right
        if (col != 8) {
            getOneRight(board, myPosition, pieceColor, row, col);
        }
        // right down
        if (row != 1 && col != 8) {
            getOneRightDown(board, myPosition, pieceColor, row, col);
        }
        // down
        if (row != 1) {
            getOneDown(board, myPosition, pieceColor, row, col);
        }
        // left down
        if (row != 1 && col != 1) {
            getOneLeftDown(board, myPosition, pieceColor, row, col);
        }
        // left
        if (col != 1) {
            getOneLeft(board, myPosition, pieceColor, row, col);
        }
        // left up
        if (row != 8 && col != 1) {
            getOneLeftUp(board, myPosition, pieceColor, row, col);
        }
        return moves;
    }
}
