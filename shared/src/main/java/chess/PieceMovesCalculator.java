package chess;

import java.util.Collection;
import java.util.LinkedList;

public interface PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
}

class KingMoveCalculator implements PieceMovesCalculator {
    private ChessPiece piece;
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;
    private ChessMove newMove;

    void addMove(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, ChessGame.TeamColor pieceColor) {
        piece = board.getPiece(newPosition);
        newMove = new ChessMove(myPosition, newPosition, null);
        if (piece != null) {
            if (piece.getTeamColor() != pieceColor) {
                moves.add(newMove);
            }
        } else {
            moves.add(newMove);
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

class KnightMoveCalculator implements PieceMovesCalculator {
    private ChessPiece piece;
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;
    private ChessMove newMove;

    void addMove(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, ChessGame.TeamColor pieceColor) {
        piece = board.getPiece(newPosition);
        newMove = new ChessMove(myPosition, newPosition, null);
        if (piece != null) {
            if (piece.getTeamColor() != pieceColor) {
                moves.add(newMove);
            }
        } else {
            moves.add(newMove);
        }
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

class PawnMoveCalculator implements PieceMovesCalculator {
    private ChessPiece piece;
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
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

class QueenMoveCalculator implements PieceMovesCalculator {
    private ChessPiece piece;
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;
    private ChessMove newMove;
    private boolean hitOtherPiece = false;

    void addMove(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, ChessGame.TeamColor pieceColor) {
        piece = board.getPiece(newPosition);
        hitOtherPiece = false;
        newMove = new ChessMove(myPosition, newPosition, null);
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

class BishopMoveCalculator implements PieceMovesCalculator {
    private ChessPiece piece;
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;
    private ChessMove newMove;
    private boolean hitOtherPiece = false;

    void addMove(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, ChessGame.TeamColor pieceColor) {
        piece = board.getPiece(newPosition);
        hitOtherPiece = false;
        newMove = new ChessMove(myPosition, newPosition, null);
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

class RookMoveCalculator implements PieceMovesCalculator {
    private ChessPiece piece;
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;
    private ChessMove newMove;
    private boolean hitOtherPiece = false;

    void addMove(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, ChessGame.TeamColor pieceColor) {
        piece = board.getPiece(newPosition);
        hitOtherPiece = false;
        newMove = new ChessMove(myPosition, newPosition, null);
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