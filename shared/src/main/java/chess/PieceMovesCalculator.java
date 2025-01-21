package chess;

import javax.swing.text.Position;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public interface PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
}

/**
Kings may move 1 square in any direction (including diagonal) to either a position occupied by an enemy
piece (capturing the enemy piece), or to an unoccupied position. TODO: A player is not allowed to make any move
that would allow the opponent to capture their King. If your King is in danger of being captured on your turn,
you must make a move that removes your King from immediate danger.
 */
class KingMovesCalculator implements PieceMovesCalculator {
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;
    private ChessMove newMove;

    void addMove(int row, int addRow, int col, int addCol, ChessBoard board, ChessGame.TeamColor pieceColor, ChessPosition myPosition) {
        newPosition = new ChessPosition(row+addRow, col+addCol);
        newMove = new ChessMove(myPosition, newPosition, null);
        if (board.getPiece(newPosition) == null) {
            moves.add(newMove);
        } else{
            if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                moves.add(newMove);
            }
        }
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        // up
        if (myPosition.getRow() != 8) {
            addMove(row, 1, col, 0, board, pieceColor, myPosition);
        }
        // diagonal right up
        if (myPosition.getRow() != 8 || myPosition.getColumn() != 8) {
            addMove(row, 1, col, 1, board, pieceColor, myPosition);
        }
        // right
        if (myPosition.getColumn() != 8) {
            addMove(row, 0, col, 1, board, pieceColor, myPosition);
        }
        // diagonal right down
        if (myPosition.getRow() != 1 && myPosition.getColumn() != 8) {
            addMove(row, -1, col, 1, board, pieceColor, myPosition);
        }
        // down
        if (myPosition.getRow() != 1) {
            addMove(row, -1, col, 0, board, pieceColor, myPosition);
        }
        // diagonal left down
        if (myPosition.getRow() != 1 || myPosition.getColumn() != 1) {
            addMove(row, -1, col, -1, board, pieceColor, myPosition);
        }
        // left
        if (myPosition.getColumn() != 1) {
            addMove(row, 0, col, -1, board, pieceColor, myPosition);
        }
        // diagonal left up
        if (myPosition.getRow() != 8 && myPosition.getColumn() != 1) {
            addMove(row, 1, col, -1, board, pieceColor, myPosition);
        }

        return moves;
    }
}

/**
Queens are the most powerful piece and may move in straight lines and diagonals as far as there is open space.
If there is an enemy piece at the end of the line, they may move to that position and capture the enemy piece.
(In simpler terms, Queens can take all moves a Rook or Bishop could take from the Queen's position).
 */
class QueenMovesCalculator implements PieceMovesCalculator {
    private ChessPiece piece;
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
    ChessPosition newPosition;
    ChessMove newMove;

    void RightTopEdge(int row, int col, int next, ChessGame.TeamColor pieceColor, ChessPosition myPosition, ChessBoard board) {
        while (true) {
            if (myPosition.getRow() == 8) {
                break;
            }
            newPosition = new ChessPosition(row+next, col+next);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (newPosition.getRow() > 8 || newPosition.getColumn() < 1) {
                break;
            }
            piece = board.getPiece(newPosition);
            if (piece != null) {
                if (piece.getTeamColor() == pieceColor) {
                    break;
                }
                moves.add(newMove);
                break;
            }
            moves.add(newMove);
            next = next + 1;
            if (newPosition.getRow() == 8 || newPosition.getColumn() == 8) {
                break;
            }
        }
    }

    void RightBottomEdge(int row, int col, int next, ChessGame.TeamColor pieceColor, ChessPosition myPosition, ChessBoard board) {
        newPosition = myPosition;
        while (true) {
            if (myPosition.getRow() == 1) {
                break;
            }
            newPosition = new ChessPosition(row-next, col+next);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (newPosition.getRow() > 8 || newPosition.getColumn() < 1) {
                break;
            }
            piece = board.getPiece(newPosition);
            if (piece != null) {
                if (piece.getTeamColor() == pieceColor) {
                    break;
                }
                moves.add(newMove);
                break;
            }
            next = next + 1;
            moves.add(newMove);
            if (newPosition.getRow() == 1 || newPosition.getColumn() == 8) {
                break;
            }
        }
    }

    void LeftBottomEdge(int row, int col, int next, ChessGame.TeamColor pieceColor, ChessPosition myPosition, ChessBoard board) {
        newPosition = myPosition;
        while (true) {
            if (myPosition.getRow() == 1) {
                break;
            }
            newPosition = new ChessPosition(row-next, col-next);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (newPosition.getRow() > 8 || newPosition.getColumn() < 1) {
                break;
            }
            piece = board.getPiece(newPosition);
            if (piece != null) {
                if (piece.getTeamColor() == pieceColor) {
                    break;
                }
                moves.add(newMove);
                break;
            }
            next = next + 1;
            moves.add(newMove);
            if (newPosition.getRow() == 1 || newPosition.getColumn() == 1) {
                break;
            }
        }
    }

    void LeftTopEdge(int row, int col, int next, ChessGame.TeamColor pieceColor, ChessPosition myPosition, ChessBoard board) {
        newPosition = myPosition;
        while (true) {
            if (myPosition.getRow() == 8) {
                break;
            }
            newPosition = new ChessPosition(row+next, col-next);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (newPosition.getRow() > 8 || newPosition.getColumn() < 1) {
                break;
            }
            piece = board.getPiece(newPosition);
            if (piece != null) {
                if (piece.getTeamColor() == pieceColor) {
                    break;
                }
                moves.add(newMove);
                break;
            }
            next = next + 1;
            moves.add(newMove);
            if (newPosition.getRow() == 8 || newPosition.getColumn() == 1) {
                break;
            }
        }
    }

    void Right(int row, int col, int next, ChessGame.TeamColor pieceColor, ChessPosition myPosition, ChessBoard board) {
        newPosition = myPosition;
        while (true) {
            if (newPosition.getColumn() == 8) {
                break;
            }
            newPosition = new ChessPosition(row, col+next);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (newPosition.getRow() > 8 || newPosition.getColumn() < 1) {
                break;
            }
            piece = board.getPiece(newPosition);
            if (piece != null) {
                if (piece.getTeamColor() == pieceColor) {
                    break;
                }
                moves.add(newMove);
                break;
            }
            next = next + 1;
            moves.add(newMove);
        }
    }

    void Left(int row, int col, int next, ChessGame.TeamColor pieceColor, ChessPosition myPosition, ChessBoard board) {
        newPosition = myPosition;
        while (true) {
            if (newPosition.getColumn() == 1) {
                break;
            }
            newPosition = new ChessPosition(row, col-next);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (newPosition.getRow() > 8 || newPosition.getColumn() < 1) {
                break;
            }
            piece = board.getPiece(newPosition);
            if (piece != null) {
                if (piece.getTeamColor() == pieceColor) {
                    break;
                }
                moves.add(newMove);
                break;
            }
            next = next + 1;
            moves.add(newMove);
        }
    }

    void Down(int row, int col, int next, ChessGame.TeamColor pieceColor, ChessPosition myPosition, ChessBoard board) {
        newPosition = myPosition;
        while (true) {
            if (newPosition.getRow() == 1) {
                break;
            }
            newPosition = new ChessPosition(row-next, col);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (newPosition.getRow() > 8 || newPosition.getColumn() < 1) {
                break;
            }
            piece = board.getPiece(newPosition);
            if (piece != null) {
                if (piece.getTeamColor() == pieceColor) {
                    break;
                }
                moves.add(newMove);
                break;
            }
            next = next + 1;
            moves.add(newMove);
        }
    }

    void Up(int row, int col, int next, ChessGame.TeamColor pieceColor, ChessPosition myPosition, ChessBoard board) {
        newPosition = myPosition;
        // up
        while (true) {
            if (newPosition.getRow() == 8) {
                break;
            }
            newPosition = new ChessPosition(row+next, col);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (newPosition.getRow() > 8 || newPosition.getColumn() < 1) {
                break;
            }
            piece = board.getPiece(newPosition);
            if (piece != null) {
                if (piece.getTeamColor() == pieceColor) {
                    break;
                }
                moves.add(newMove);
                break;
            }
            next = next + 1;
            moves.add(newMove);
        }
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int next = 1;

        RightTopEdge(row, col, next, pieceColor, myPosition, board);
        RightBottomEdge(row, col, 1, pieceColor, myPosition, board);
        LeftBottomEdge(row, col, 1, pieceColor, myPosition, board);
        LeftTopEdge(row, col, 1, pieceColor, myPosition, board);
        Right(row, col, 1, pieceColor, myPosition, board);
        Left(row, col, 1, pieceColor, myPosition, board);
        Down(row, col, 1, pieceColor, myPosition, board);
        Up(row, col, 1, pieceColor, myPosition, board);

        return moves;
    }
}
/**
Bishops move in diagonal lines as far as there is open space.
If there is an enemy piece at the end of the diagonal,
the bishop may move to that position and capture the enemy piece.
 */
class BishopMovesCalculator implements PieceMovesCalculator {
    private ChessPiece piece;
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;
    private ChessMove newMove;

    void addMove(ChessPosition myPosition, int row, int col, int next, int checkRow, int checkCol, ChessBoard board, ChessGame.TeamColor pieceColor, int caseCheck) {
        while (true) {
            if (myPosition.getRow() == checkRow) {
                break;
            }
            if (caseCheck == 1) { /// Right Top
                newPosition = new ChessPosition(row + next, col + next);
            } else if (caseCheck == 2) { /// Right Bottom
                newPosition = new ChessPosition(row-next, col+next);
            } else if (caseCheck == 3) { /// Left Bottom
                newPosition = new ChessPosition(row-next, col-next);
            } else if (caseCheck == 4) { /// Left Top
                newPosition = new ChessPosition(row+next, col-next);
            }
            newMove = new ChessMove(myPosition, newPosition, null);
            piece = board.getPiece(newPosition);
            if (piece != null) {
                if (piece.getTeamColor() == pieceColor) {
                    break;
                }
                moves.add(newMove);
                break;
            }
            next = next + 1;
            moves.add(newMove);
            if (newPosition.getRow() == checkRow || newPosition.getColumn() == checkCol) {
                break;
            }
        }
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int next = 1;

        // Right Top Edge
        addMove(myPosition, row, col, next, 8, 8, board, pieceColor, 1);
        // Right Bottom Edge
        addMove(myPosition, row, col, 1, 1, 8, board, pieceColor, 2);
        // Left Bottom Edge
        addMove(myPosition, row, col, 1, 1, 1, board, pieceColor, 3);
        // Left Top Edge
        addMove(myPosition, row, col, 1, 8, 1, board, pieceColor, 4);

        return moves;
    }
}

/**
Knights move in an L shape, moving 2 squares in one direction and 1 square in the other direction.
Knights are the only piece that can ignore pieces in the in-between squares (they can "jump" over other pieces).
They can move to squares occupied by an enemy piece and capture the enemy piece, or to unoccupied squares.
 */
class KnightMovesCalculator implements PieceMovesCalculator {
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;
    private ChessMove newMove;

    void addMove(ChessPosition myPosition, ChessPosition newPosition) {
        newMove = new ChessMove(myPosition, newPosition, null);
        moves.add(newMove);
    }
    void moveForwardRight(int row, int col, ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor pieceColor) {
        newPosition = new ChessPosition(row+2, col+1);
        if (board.getPiece(newPosition) != null) {
            if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                addMove(myPosition, newPosition);
            }
        } else {
            addMove(myPosition, newPosition);
        }
    }
    void moveForwardLeft(int row, int col, ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor pieceColor) {
        newPosition = new ChessPosition(row+2, col-1);
        if (board.getPiece(newPosition) != null) {
            if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                addMove(myPosition, newPosition);
            }
        } else {
            addMove(myPosition, newPosition);
        }
    }
    void moveBackRight(int row, int col, ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor pieceColor){
        newPosition = new ChessPosition(row-2, col+1);
        if (board.getPiece(newPosition) != null) {
            if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                addMove(myPosition, newPosition);
            }
        } else {
            addMove(myPosition, newPosition);
        }
    }
    void moveBackLeft(int row, int col, ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor pieceColor){
        newPosition = new ChessPosition(row-2, col-1);
        if (board.getPiece(newPosition) != null) {
            if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                addMove(myPosition, newPosition);
            }
        } else {
            addMove(myPosition, newPosition);
        }
    }
    void moveLeftDown(int row, int col, ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor pieceColor){
        newPosition = new ChessPosition(row-1, col-2);
        if (board.getPiece(newPosition) != null) {
            if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                addMove(myPosition, newPosition);
            }
        } else {
            addMove(myPosition, newPosition);
        }
    }
    void moveLeftUp(int row, int col, ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor pieceColor){
        newPosition = new ChessPosition(row+1, col-2);
        if (board.getPiece(newPosition) != null) {
            if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                addMove(myPosition, newPosition);
            }
        } else {
            addMove(myPosition, newPosition);
        }
    }
    void moveRightDown(int row, int col, ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor pieceColor){
        newPosition = new ChessPosition(row-1, col+2);
        if (board.getPiece(newPosition) != null) {
            if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                addMove(myPosition, newPosition);
            }
        } else {
            addMove(myPosition, newPosition);
        }
    }
    void moveRightUp(int row, int col, ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor pieceColor){
        newPosition = new ChessPosition(row+1, col+2);
        if (board.getPiece(newPosition) != null) {
            if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                addMove(myPosition, newPosition);
            }
        } else {
            addMove(myPosition, newPosition);
        }
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        // edge: bottom/top
        if (myPosition.getRow() == 1) {
            if (myPosition.getColumn() == 1) { // corner
                moveForwardRight(row, col, myPosition, board, pieceColor);
                moveRightUp(row, col, myPosition, board, pieceColor);
            } else if (myPosition.getColumn() == 8) { // other corner
                moveForwardLeft(row, col, myPosition, board, pieceColor);
                moveLeftUp(row, col, myPosition, board, pieceColor);
            } else {
                moveForwardRight(row, col, myPosition, board, pieceColor);
                moveForwardLeft(row, col, myPosition, board, pieceColor);
                moveLeftUp(row, col, myPosition, board, pieceColor);
                moveRightUp(row, col, myPosition, board, pieceColor);
            }
        }
        // edge: top/bottom
        if (myPosition.getRow() == 8) {
            if (myPosition.getColumn() == 1) { // corner
                moveBackRight(row, col, myPosition, board, pieceColor);
                moveRightDown(row, col, myPosition, board, pieceColor);
            } else if (myPosition.getColumn() == 8) { // other corner
                moveBackLeft(row, col, myPosition, board, pieceColor);
                moveLeftDown(row, col, myPosition, board, pieceColor);
            } else {
                moveBackRight(row, col, myPosition, board, pieceColor);
                moveBackLeft(row, col, myPosition, board, pieceColor);
                moveLeftDown(row, col, myPosition, board, pieceColor);
                moveRightDown(row, col, myPosition, board, pieceColor);
            }
        }
        // edge: left/right
        if (myPosition.getColumn() == 1) {
            if (myPosition.getRow() == 1 || myPosition.getRow() == 8) {
                ///  Do nothing, was covered earlier in top and bottom
            } else {
                moveForwardRight(row, col, myPosition, board, pieceColor);
                moveBackRight(row, col, myPosition, board, pieceColor);
                moveRightDown(row, col, myPosition, board, pieceColor);
                moveRightUp(row, col, myPosition, board, pieceColor);
            }
        }
        // edge: right/left
        if (myPosition.getColumn() == 8) {
            if (myPosition.getRow() == 1 || myPosition.getRow() == 8) {
                ///  Do nothing, was covered earlier in top and bottom
            } else {
                moveForwardLeft(row, col, myPosition, board, pieceColor);
                moveBackLeft(row, col, myPosition, board, pieceColor);
                moveLeftDown(row, col, myPosition, board, pieceColor);
                moveLeftUp(row, col, myPosition, board, pieceColor);
            }
        }
        // basic
        if (myPosition.getRow() != 1 && myPosition.getRow() != 8 && myPosition.getColumn() != 1 && myPosition.getColumn() != 8) {
            moveForwardRight(row, col, myPosition, board, pieceColor);
            moveForwardLeft(row, col, myPosition, board, pieceColor);
            moveBackRight(row, col, myPosition, board, pieceColor);
            moveBackLeft(row, col, myPosition, board, pieceColor);
            moveLeftDown(row, col, myPosition, board, pieceColor);
            moveLeftUp(row, col, myPosition, board, pieceColor);
            moveRightDown(row, col, myPosition, board, pieceColor);
            moveRightUp(row, col, myPosition, board, pieceColor);
        }
        return moves;
    }
}

/**
Rooks may move in straight lines as far as there is open space.
If there is an enemy piece at the end of the line, rooks may move to that position and capture the enemy piece.
 */
class RookMovesCalculator implements PieceMovesCalculator {
    private ChessPiece piece;
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();

    void addMove(ChessPosition newPosition, ChessPosition myPosition, ChessBoard board, ChessMove newMove, ChessGame.TeamColor pieceColor, int next, int row, int col, char caseCheck) {
        while (true) {
            if (caseCheck == 'r') { /// right
                if (newPosition.getColumn() == 8) {
                    break;
                }
                newPosition = new ChessPosition(row, col + next);
            } else if (caseCheck == 'l') { /// left
                if (newPosition.getColumn() == 1) {
                    break;
                }
                newPosition = new ChessPosition(row, col-next);
            } else if (caseCheck == 'd') { /// down
                if (newPosition.getRow() == 1) {
                    break;
                }
                newPosition = new ChessPosition(row-next, col);
            } else if (caseCheck == 'u') { /// up
                if (newPosition.getRow() == 8) {
                    break;
                }
                newPosition = new ChessPosition(row+next, col);
            }
            newMove = new ChessMove(myPosition, newPosition, null);
            piece = board.getPiece(newPosition);
            if (piece != null) {
                if (piece.getTeamColor() == pieceColor) {
                    break;
                }
                moves.add(newMove);
                break;
            }
            next = next + 1;
            moves.add(newMove);
        }
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int next = 1;
        ChessPosition newPosition = myPosition;
        ChessMove newMove = new ChessMove(myPosition, newPosition, null);

        /// right
        addMove(newPosition, myPosition, board, newMove, pieceColor, next, row, col, 'r');
        /// left
        addMove(newPosition, myPosition, board, newMove, pieceColor, 1, row, col, 'l');
        /// down
        addMove(newPosition, myPosition, board, newMove, pieceColor, 1, row, col, 'd');
        /// up
        addMove(newPosition, myPosition, board, newMove, pieceColor, 1, row, col, 'u');

        return moves;
    }
}

/**
Pawns normally may move forward one square if that square is unoccupied,
though if it is the first time that pawn is being moved,
it may be moved forward 2 squares (provided both squares are unoccupied).
Pawns cannot capture forward, but instead capture forward diagonally (1 square forward and 1 square sideways).
They may only move diagonally like this if capturing an enemy piece.
When a pawn moves to the end of the board (row 8 for white and row 1 for black),
they get promoted and are replaced with the player's choice of Rook, Knight, Bishop, or Queen
(they cannot stay a Pawn or become King).
 */
class PawnMovesCalculator implements PieceMovesCalculator {
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
    private ChessPosition newPosition;
    private ChessMove newMove;

    void Promotion(ChessPosition myPosition, ChessPosition newPosition) {
        newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
        moves.add(newMove);
        newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
        moves.add(newMove);
        newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
        moves.add(newMove);
        newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
        moves.add(newMove);
    }

    void Capture(ChessBoard board, ChessPosition myPosition, ChessPosition diagPosition, ChessGame.TeamColor pieceColor, int checkRow) {
        if (board.getPiece(diagPosition) != null) {
            newPosition = diagPosition;
            newMove = new ChessMove(myPosition, newPosition, null);
            if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                if (newPosition.getRow() == checkRow) {
                    Promotion(myPosition, newPosition);
                } else {
                    moves.add(newMove);
                }
            }
        }
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition diagPosition1 = new ChessPosition(row+1, col+1);
        ChessPosition diagPosition2 = new ChessPosition(row+1, col-1);
        ChessPosition diagPosition3 = new ChessPosition(row-1, col+1);
        ChessPosition diagPosition4 = new ChessPosition(row-1, col-1);
        ChessPosition tempPosition;
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            // first move
            if (myPosition.getRow() == 2) {
                tempPosition = new ChessPosition(row+1, col);
                newPosition = new ChessPosition(row+2, col);
                newMove = new ChessMove(myPosition, newPosition,null);
                if (board.getPiece(newPosition) == null && board.getPiece(tempPosition) == null) {
                    moves.add(newMove);
                }
            }
            // basic move forward
            if (myPosition.getRow() != 8) {
                newPosition = new ChessPosition(row + 1, col);
                newMove = new ChessMove(myPosition, newPosition, null);
                if (newPosition.getRow() != 8) { ///  if 8 then it should be promotion
                    if (board.getPiece(newPosition) == null) {
                        moves.add(newMove);
                    }
                }
                // capture
                if (myPosition.getColumn() != 8) {
                    Capture(board, myPosition, diagPosition1, pieceColor, 8);
                }
                if (myPosition.getColumn() != 1) {
                    Capture(board, myPosition, diagPosition2, pieceColor, 8);
                }
                newPosition = new ChessPosition(row + 1, col); /// resets in case of capture
            }
            // promotion
            if (myPosition.getRow() == 8 || newPosition.getRow() == 8) {
                Promotion(myPosition, newPosition);
            }
        } else {
            // first move
            if (myPosition.getRow() == 7) {
                tempPosition = new ChessPosition(row-1, col);
                newPosition = new ChessPosition(row-2, col);
                newMove = new ChessMove(myPosition, newPosition, null);
                if (board.getPiece(newPosition) == null && board.getPiece(tempPosition) == null) {
                    moves.add(newMove);
                }
            }
            // basic move forward
            if (myPosition.getRow() != 1) {
                newPosition = new ChessPosition(row-1, col);
                newMove = new ChessMove(myPosition, newPosition, null);
                if (newPosition.getRow() != 1) {
                    if (board.getPiece(newPosition) == null) {
                        moves.add(newMove);
                    }
                }
                // capture
                if (myPosition.getColumn() != 8) {
                    Capture(board, myPosition, diagPosition3, pieceColor, 1);
                }
                if (myPosition.getColumn() != 1) {
                    Capture(board, myPosition, diagPosition4, pieceColor, 1);
                }
                newPosition = new ChessPosition(row-1, col); /// resets in case of capture
            }
            // promotion
            if (myPosition.getRow() == 1 || newPosition.getRow() == 1) {
                Promotion(myPosition, newPosition);
            }
        }
        return moves;
    }
}

