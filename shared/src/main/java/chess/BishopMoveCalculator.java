package chess;

import java.util.Collection;
import java.util.LinkedList;

class BishopMoveCalculator implements PieceMovesCalculator {
    private final Collection<ChessMove> moves = new LinkedList<ChessMove>();

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        SharedMoves sharedMoves = new SharedMoves(moves);
        sharedMoves.getDiagonalMoves(row, col, board, myPosition, pieceColor);
        return moves;
    }
}
