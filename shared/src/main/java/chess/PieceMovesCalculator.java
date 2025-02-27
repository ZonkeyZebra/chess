package chess;

import java.util.Collection;
import java.util.LinkedList;

public interface PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
}