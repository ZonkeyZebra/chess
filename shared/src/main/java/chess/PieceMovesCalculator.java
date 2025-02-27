package chess;

import java.util.Collection;

public interface PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
}

class SharedMoves {
    Collection<ChessMove> moves;
    private ChessPosition newPosition;
    boolean hitOtherPiece;

    public SharedMoves(Collection<ChessMove> moves) {
        this.moves = moves;
    }

    public void addMoveKingKnight(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, ChessGame.TeamColor pieceColor) {
        ChessPiece piece = board.getPiece(newPosition);
        ChessMove newMove = new ChessMove(myPosition, newPosition, null);
        if (piece != null) {
            if (piece.getTeamColor() != pieceColor) {
                moves.add(newMove);
            }
        } else {
            moves.add(newMove);
        }
    }

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

    void getUpDownLeftRight(int row, int col, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
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
    }

    void getDiagonalMoves(int row, int col, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
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
    }
}