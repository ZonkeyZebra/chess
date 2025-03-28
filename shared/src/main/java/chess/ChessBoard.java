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
    private final ChessPiece[][] board = new ChessPiece[8][8];
    private final ChessGame.TeamColor black = ChessGame.TeamColor.BLACK;
    private final ChessGame.TeamColor white = ChessGame.TeamColor.WHITE;
    private final ChessPiece.PieceType rook = ChessPiece.PieceType.ROOK;
    private final ChessPiece.PieceType knight = ChessPiece.PieceType.KNIGHT;
    private final ChessPiece.PieceType pawn = ChessPiece.PieceType.PAWN;
    private final ChessPiece.PieceType queen = ChessPiece.PieceType.QUEEN;
    private final ChessPiece.PieceType king = ChessPiece.PieceType.KING;
    private final ChessPiece.PieceType bishop = ChessPiece.PieceType.BISHOP;
    private final ChessPosition thePosition = new ChessPosition(0, 0);
    private final ChessPiece thePiece = new ChessPiece(white, rook);

    public ChessBoard() {

    }

    public ChessBoard(ChessBoard board) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                board.getPiece(new ChessPosition(i,j));
                this.board[i-1][j-1] = board.getPiece(new ChessPosition(i,j));
            }
        }
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

    public void removePiece(ChessPosition position) {
        board[position.getRow() - 1][position.getColumn() - 1] = null;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow() - 1;
        int col = position.getColumn() - 1;
        /// check bounds
        if (row > 7 || row < 0 || col > 7 || col < 0) {
            return null;
        }
        return board[row][col];
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
        return Objects.equals(thePosition, that.thePosition);
    }

    @Override
    public int hashCode() {
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