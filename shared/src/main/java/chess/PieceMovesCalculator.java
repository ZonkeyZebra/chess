package chess;

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
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition newPosition = myPosition;
        ChessMove newMove;
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        // up
        if (myPosition.getRow() != 8) {
            newPosition = new ChessPosition(row+1, col);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (board.getPiece(newPosition) == null) {
                moves.add(newMove);
            } else{
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    moves.add(newMove);
                }
            }
        }
        // diagonal right up
        if (myPosition.getRow() != 8 || myPosition.getColumn() != 8) {
            newPosition = new ChessPosition(row+1, col+1);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (board.getPiece(newPosition) == null) {
                moves.add(newMove);
            } else{
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    moves.add(newMove);
                }
            }
        }
        // right
        if (myPosition.getColumn() != 8) {
            newPosition = new ChessPosition(row, col+1);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (board.getPiece(newPosition) == null) {
                moves.add(newMove);
            } else{
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    moves.add(newMove);
                }
            }
        }
        // diagonal right down
        if (myPosition.getRow() != 1 && myPosition.getColumn() != 8) {
            newPosition = new ChessPosition(row-1, col+1);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (board.getPiece(newPosition) == null) {
                moves.add(newMove);
            } else{
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    moves.add(newMove);
                }
            }
        }
        // down
        if (myPosition.getRow() != 1) {
            newPosition = new ChessPosition(row-1, col);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (board.getPiece(newPosition) == null) {
                moves.add(newMove);
            } else{
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    moves.add(newMove);
                }
            }
        }
        // diagonal left down
        if (myPosition.getRow() != 1 || myPosition.getColumn() != 1) {
            newPosition = new ChessPosition(row-1, col-1);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (board.getPiece(newPosition) == null) {
                moves.add(newMove);
            } else{
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    moves.add(newMove);
                }
            }
        }
        // left
        if (myPosition.getColumn() != 1) {
            newPosition = new ChessPosition(row, col-1);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (board.getPiece(newPosition) == null) {
                moves.add(newMove);
            } else{
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    moves.add(newMove);
                }
            }
        }
        // diagonal left up
        if (myPosition.getRow() != 8 && myPosition.getColumn() != 1) {
            newPosition = new ChessPosition(row+1, col-1);
            newMove = new ChessMove(myPosition, newPosition, null);
            if (board.getPiece(newPosition) == null) {
                moves.add(newMove);
            } else{
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    moves.add(newMove);
                }
            }
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
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int next = 1;
        ChessPosition newPosition = myPosition;
        ChessMove newMove;
        // Right Top Edge
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
            next = next + 1;
            moves.add(newMove);
            if (newPosition.getRow() == 8 || newPosition.getColumn() == 8) {
                break;
            }
        }
        // Right Bottom Edge
        next = 1;
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
        // Left Bottom Edge
        next = 1;
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
        // Left Top Edge
        next = 1;
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
        // right
        next = 1;
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
        next = 1;
        newPosition = myPosition;
        // left
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
        next = 1;
        newPosition = myPosition;
        // down
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
        next = 1;
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

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int next = 1;
        ChessPosition newPosition = myPosition;
        ChessMove newMove;
        // Right Top Edge
        while (true) {
            if (myPosition.getRow() == 8) {
                break;
            }
            newPosition = new ChessPosition(row+next, col+next);
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
            if (newPosition.getRow() == 8 || newPosition.getColumn() == 8) {
                break;
            }
        }
        // Right Bottom Edge
        next = 1;
        while (true) {
            if (myPosition.getRow() == 1) {
                break;
            }
            newPosition = new ChessPosition(row-next, col+next);
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
            if (newPosition.getRow() == 1 || newPosition.getColumn() == 8) {
                break;
            }
        }
        // Left Bottom Edge
        next = 1;
        while (true) {
            if (myPosition.getRow() == 1) {
                break;
            }
            newPosition = new ChessPosition(row-next, col-next);
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
            if (newPosition.getRow() == 1 || newPosition.getColumn() == 1) {
                break;
            }
        }
        // Left Top Edge
        next = 1;
        while (true) {
            if (myPosition.getRow() == 8) {
                break;
            }
            newPosition = new ChessPosition(row+next, col-next);
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
            if (newPosition.getRow() == 8 || newPosition.getColumn() == 1) {
                break;
            }
        }
        return moves;
    }
}

/**
Knights move in an L shape, moving 2 squares in one direction and 1 square in the other direction.
Knights are the only piece that can ignore pieces in the in-between squares (they can "jump" over other pieces).
They can move to squares occupied by an enemy piece and capture the enemy piece, or to unoccupied squares.
 */
class KnightMovesCalculator implements PieceMovesCalculator {
    private ChessPiece piece;
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }
}

/**
Rooks may move in straight lines as far as there is open space.
If there is an enemy piece at the end of the line, rooks may move to that position and capture the enemy piece.
 */
class RookMovesCalculator implements PieceMovesCalculator {
    private ChessPiece piece;
    private Collection<ChessMove> moves = new LinkedList<ChessMove>();
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int next = 1;
        ChessPosition newPosition = myPosition;
        ChessMove newMove;
        // right
        while (true) {
            if (newPosition.getColumn() == 8) {
                break;
            }
            newPosition = new ChessPosition(row, col+next);
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
        next = 1;
        newPosition = myPosition;
        // left
        while (true) {
            if (newPosition.getColumn() == 1) {
                break;
            }
            newPosition = new ChessPosition(row, col-next);
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
        next = 1;
        newPosition = myPosition;
        // down
        while (true) {
            if (newPosition.getRow() == 1) {
                break;
            }
            newPosition = new ChessPosition(row-next, col);
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
        next = 1;
        newPosition = myPosition;
        // up
        while (true) {
            if (newPosition.getRow() == 8) {
                break;
            }
            newPosition = new ChessPosition(row+next, col);
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
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPosition newPosition = myPosition;
        ChessMove newMove;
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
                    if (board.getPiece(diagPosition1) != null) {
                        newPosition = diagPosition1;
                        newMove = new ChessMove(myPosition, newPosition, null);
                        if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                            if (newPosition.getRow() == 8) {
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
                                moves.add(newMove);
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
                                moves.add(newMove);
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
                                moves.add(newMove);
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
                                moves.add(newMove);
                            } else {
                                moves.add(newMove);
                            }
                        }
                    }
                }
                if (myPosition.getColumn() != 1) {
                    if (board.getPiece(diagPosition2) != null) {
                        newPosition = diagPosition2;
                        newMove = new ChessMove(myPosition, newPosition, null);
                        if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                            if (newPosition.getRow() == 8) {
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
                                moves.add(newMove);
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
                                moves.add(newMove);
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
                                moves.add(newMove);
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
                                moves.add(newMove);
                            } else {
                                moves.add(newMove);
                            }
                        }
                    }
                }
                newPosition = new ChessPosition(row + 1, col); /// resets in case of capture
            }
            // promotion
            if (myPosition.getRow() == 8 || newPosition.getRow() == 8) {
                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
                moves.add(newMove);
                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
                moves.add(newMove);
                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
                moves.add(newMove);
                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
                moves.add(newMove);
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
                    if (board.getPiece(diagPosition3) != null) {
                        newPosition = diagPosition3;
                        newMove = new ChessMove(myPosition, newPosition, null);
                        if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                            if (newPosition.getRow() == 1) {
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
                                moves.add(newMove);
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
                                moves.add(newMove);
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
                                moves.add(newMove);
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
                                moves.add(newMove);
                            } else {
                                moves.add(newMove);
                            }
                        }
                    }
                }
                if (myPosition.getColumn() != 1) {
                    if (board.getPiece(diagPosition4) != null) {
                        newPosition = diagPosition4;
                        newMove = new ChessMove(myPosition, newPosition, null);
                        if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                            if (newPosition.getRow() == 1) {
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
                                moves.add(newMove);
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
                                moves.add(newMove);
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
                                moves.add(newMove);
                                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
                                moves.add(newMove);
                            } else {
                                moves.add(newMove);
                            }
                        }
                    }
                }
                newPosition = new ChessPosition(row-1, col); /// resets in case of capture
            }
            // promotion
            if (myPosition.getRow() == 1 || newPosition.getRow() == 1) {
                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
                moves.add(newMove);
                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
                moves.add(newMove);
                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
                moves.add(newMove);
                newMove = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
                moves.add(newMove);
            }
        }
        return moves;
    }
}

