package chess;

import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor team;
    private ChessBoard board;
    private Collection<ChessMove> validMoves;
    private ChessPiece piece;
    private final ChessPiece.PieceType king = ChessPiece.PieceType.KING;

    public ChessGame() {
        setBoard(new ChessBoard());
        setTeamTurn(TeamColor.WHITE);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
    }

    /**
     * Set's which teams turn it is
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        board = getBoard();
        piece = board.getPiece(startPosition);
        /// If there is no piece at that location, this method returns null
        if (piece == null) {
            return null;
        }
        /// A move is valid if it is a "piece move" for the piece at the input location and making that move would not leave the team’s king in danger of check.
        validMoves = piece.pieceMoves(board, startPosition);
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        boolean isValid = false;
        TeamColor currentTeamTurn = getTeamTurn();
        for (ChessMove validMove : validMoves) {
            if (validMove == move) {
                isValid = true;
                break;
            }
        }
        /// If the move is illegal (not valid or corresponding team color), it throws an InvalidMoveException.
        if (!isValid) {
            throw new InvalidMoveException();
        }
        /// Receives a given move and executes it, provided it is a legal move.
        // Which piece do I associate with
        // Use set board to make move
        throw new RuntimeException("Not implemented");
    }


    /**
     * Determines if the given team is in check
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        /// Returns true if the specified team’s King could be captured by an opposing piece.
        ChessPiece isPiece;
        ChessPiece landingPiece;
        board = getBoard();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                isPiece = board.getPiece(new ChessPosition(i,j));
                if (isPiece != null) {
                    validMoves = isPiece.pieceMoves(board, new ChessPosition(i,j));
                    for (ChessMove validMove : validMoves) {
                        landingPiece = board.getPiece(validMove.getEndPosition());
                        if (landingPiece != null) {
                            if (landingPiece.getPieceType() == king && landingPiece.getTeamColor() == teamColor) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        /// Returns true if the given team has no way to protect their king from being captured.
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having no valid moves
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        /// Returns true if the given team has no legal moves but their king is not in immediate danger.
        if (validMoves == null && !isInCheck(teamColor) && !isInCheckmate(teamColor)) {
            return true;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    private void getChessPieceValidMoves() {
        ChessPiece isPiece;
        board = getBoard();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                isPiece = board.getPiece(new ChessPosition(i,j));
                if (isPiece != null) {
                    validMoves = validMoves(new ChessPosition(i,j));
                }
            }
        }
    }

    private boolean oldisInCheck(TeamColor teamColor) {
        /// Returns true if the specified team’s King could be captured by an opposing piece.
        ChessPiece landingPiece;
        for (ChessMove validMove : validMoves) {
            landingPiece = board.getPiece(validMove.getEndPosition());
            if (landingPiece.getPieceType() == king && landingPiece.getTeamColor() != teamColor) {
                return true;
            }
        }
        return false;
    }

}
