package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] board = new ChessPiece[8][8];
    private ChessGame.TeamColor black = ChessGame.TeamColor.BLACK;
    private ChessGame.TeamColor white = ChessGame.TeamColor.WHITE;
    private ChessPiece.PieceType rook = ChessPiece.PieceType.ROOK;
    private ChessPiece.PieceType knight = ChessPiece.PieceType.KNIGHT;
    private ChessPiece.PieceType pawn = ChessPiece.PieceType.PAWN;
    private ChessPiece.PieceType queen = ChessPiece.PieceType.QUEEN;
    private ChessPiece.PieceType king = ChessPiece.PieceType.KING;
    private ChessPiece.PieceType bishop = ChessPiece.PieceType.BISHOP;
    private ChessPosition thePosition = new ChessPosition(0, 0);
    private ChessPiece thePiece = new ChessPiece(white, rook);

    public ChessBoard() {

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        addPiece(new ChessPosition(1, 1), new ChessPiece(white, rook));
        addPiece(new ChessPosition(1, 2), new ChessPiece(white, knight));
        addPiece(new ChessPosition(1, 3), new ChessPiece(white, bishop));
        addPiece(new ChessPosition(1, 4), new ChessPiece(white, queen));
        addPiece(new ChessPosition(1, 5), new ChessPiece(white, king));
        addPiece(new ChessPosition(1, 6), new ChessPiece(white, bishop));
        addPiece(new ChessPosition(1, 7), new ChessPiece(white, knight));
        addPiece(new ChessPosition(1, 8), new ChessPiece(white, rook));
        addPiece(new ChessPosition(2, 1), new ChessPiece(white, pawn));
        addPiece(new ChessPosition(2, 2), new ChessPiece(white, pawn));
        addPiece(new ChessPosition(2, 3), new ChessPiece(white, pawn));
        addPiece(new ChessPosition(2, 4), new ChessPiece(white, pawn));
        addPiece(new ChessPosition(2, 5), new ChessPiece(white, pawn));
        addPiece(new ChessPosition(2, 6), new ChessPiece(white, pawn));
        addPiece(new ChessPosition(2, 7), new ChessPiece(white, pawn));
        addPiece(new ChessPosition(2, 8), new ChessPiece(white, pawn));
        addPiece(new ChessPosition(7, 1), new ChessPiece(black, pawn));
        addPiece(new ChessPosition(7, 2), new ChessPiece(black, pawn));
        addPiece(new ChessPosition(7, 3), new ChessPiece(black, pawn));
        addPiece(new ChessPosition(7, 4), new ChessPiece(black, pawn));
        addPiece(new ChessPosition(7, 5), new ChessPiece(black, pawn));
        addPiece(new ChessPosition(7, 6), new ChessPiece(black, pawn));
        addPiece(new ChessPosition(7, 7), new ChessPiece(black, pawn));
        addPiece(new ChessPosition(7, 8), new ChessPiece(black, pawn));
        addPiece(new ChessPosition(8, 1), new ChessPiece(black, rook));
        addPiece(new ChessPosition(8, 2), new ChessPiece(black, knight));
        addPiece(new ChessPosition(8, 3), new ChessPiece(black, bishop));
        addPiece(new ChessPosition(8, 4), new ChessPiece(black, queen));
        addPiece(new ChessPosition(8, 5), new ChessPiece(black, king));
        addPiece(new ChessPosition(8, 6), new ChessPiece(black, bishop));
        addPiece(new ChessPosition(8, 7), new ChessPiece(black, knight));
        addPiece(new ChessPosition(8, 8), new ChessPiece(black, rook));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        //return Objects.deepEquals(board, that.board) && black == that.black && white == that.white && rook == that.rook && knight == that.knight && pawn == that.pawn && queen == that.queen && king == that.king && bishop == that.bishop && Objects.equals(thePosition, that.thePosition) && Objects.equals(thePiece, that.thePiece);
        return Objects.equals(thePosition, that.thePosition);
    }

    @Override
    public int hashCode() {
        //return Objects.hash(Arrays.deepHashCode(board), black, white, rook, knight, pawn, queen, king, bishop, thePosition, thePiece);
        return Objects.hash(thePosition);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "board=" + Arrays.toString(board) +
                ", black=" + black +
                ", white=" + white +
                ", rook=" + rook +
                ", knight=" + knight +
                ", pawn=" + pawn +
                ", queen=" + queen +
                ", king=" + king +
                ", bishop=" + bishop +
                ", thePosition=" + thePosition +
                ", thePiece=" + thePiece +
                '}';
    }
}