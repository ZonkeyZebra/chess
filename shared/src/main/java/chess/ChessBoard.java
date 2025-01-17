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
    private ChessPosition thePosition = new ChessPosition(0,0);
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
        board[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // WHITE
        thePosition.setRow(1);
        thePosition.setCol(1);
        thePiece.setPieceColor(white);
        thePiece.setType(rook);
        addPiece(thePosition, thePiece);
        thePosition.setCol(2);
        thePiece.setType(knight);
        addPiece(thePosition, thePiece);
        thePosition.setCol(3);
        thePiece.setType(bishop);
        addPiece(thePosition, thePiece);
        thePosition.setCol(4);
        thePiece.setType(queen);
        addPiece(thePosition, thePiece);
        thePosition.setCol(5);
        thePiece.setType(king);
        addPiece(thePosition, thePiece);
        thePosition.setCol(6);
        thePiece.setType(bishop);
        addPiece(thePosition, thePiece);
        thePosition.setCol(7);
        thePiece.setType(knight);
        addPiece(thePosition, thePiece);
        thePosition.setCol(8);
        thePiece.setType(rook);
        addPiece(thePosition, thePiece);
        thePosition.setRow(2);
        thePosition.setCol(1);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(2);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(3);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(4);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(5);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(6);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(7);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(8);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        // BLACK
        thePosition.setRow(8);
        thePosition.setCol(1);
        thePiece.setPieceColor(black);
        thePiece.setType(rook);
        addPiece(thePosition, thePiece);
        thePosition.setCol(2);
        thePiece.setType(knight);
        addPiece(thePosition, thePiece);
        thePosition.setCol(3);
        thePiece.setType(bishop);
        addPiece(thePosition, thePiece);
        thePosition.setCol(4);
        thePiece.setType(queen);
        addPiece(thePosition, thePiece);
        thePosition.setCol(5);
        thePiece.setType(king);
        addPiece(thePosition, thePiece);
        thePosition.setCol(6);
        thePiece.setType(bishop);
        addPiece(thePosition, thePiece);
        thePosition.setCol(7);
        thePiece.setType(knight);
        addPiece(thePosition, thePiece);
        thePosition.setCol(8);
        thePiece.setType(rook);
        addPiece(thePosition, thePiece);
        thePosition.setRow(7);
        thePosition.setCol(1);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(2);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(3);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(4);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(5);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(6);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(7);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
        thePosition.setCol(8);
        thePiece.setType(pawn);
        addPiece(thePosition, thePiece);
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
