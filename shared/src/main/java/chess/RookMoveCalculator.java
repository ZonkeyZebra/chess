package chess;

import java.util.Collection;
import java.util.LinkedList;

class RookMoveCalculator implements PieceMovesCalculator {
    private final Collection<ChessMove> moves = new LinkedList<ChessMove>();

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        SharedMoves sharedMoves  = new SharedMoves(moves);
        sharedMoves.getUpDownLeftRight(row, col, board, myPosition, pieceColor);
        return moves;
    }
}
