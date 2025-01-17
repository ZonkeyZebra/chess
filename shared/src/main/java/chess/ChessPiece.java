package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        setPieceColor(pieceColor);
        setType(type);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    public void setPieceColor(ChessGame.TeamColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece thePiece = board.getPiece(myPosition);
        PieceType type = thePiece.getPieceType();

        if (type == PieceType.KING) {
            PieceMovesCalculator movePieces = new KingMovesCalculator();
            return movePieces.pieceMoves(board, myPosition);
        } else if (type == PieceType.QUEEN) {
            PieceMovesCalculator movePieces = new QueenMovesCalculator();
            return movePieces.pieceMoves(board, myPosition);
        } else if (type == PieceType.BISHOP) {
            PieceMovesCalculator movePieces = new BishopMovesCalculator();
            return movePieces.pieceMoves(board, myPosition);
        } else if (type == PieceType.KNIGHT) {
            PieceMovesCalculator movePieces = new KnightMovesCalculator();
            return movePieces.pieceMoves(board, myPosition);
        } else if (type == PieceType.ROOK) {
            PieceMovesCalculator movePieces = new RookMovesCalculator();
            return movePieces.pieceMoves(board, myPosition);
        } else if (type == PieceType.PAWN) {
            PieceMovesCalculator movePieces = new PawnMovesCalculator();
            return movePieces.pieceMoves(board, myPosition);
        } else {
            throw new RuntimeException("Not implemented");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}
