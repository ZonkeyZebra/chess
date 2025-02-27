package chess;

import java.util.Collection;

public interface PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
}

class SharedMoves {
    Collection<ChessMove> moves;

    public SharedMoves(Collection<ChessMove> moves) {
        this.moves = moves;
    }

    public void addMoveKingKinght(ChessBoard board, ChessPosition myPosition, ChessPosition newPosition, ChessGame.TeamColor pieceColor) {
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
}