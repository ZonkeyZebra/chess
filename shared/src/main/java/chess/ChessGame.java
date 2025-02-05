package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        Collection<ChessMove> kingMoves;
        Collection<ChessMove> removeMoves = new ArrayList<>();
        boolean moveWillCheck = false;
        ChessGame.TeamColor teamColor;
        ChessBoard fakeBoard = new ChessBoard(board);

        /// If there is no piece at that location, this method returns null
        if (piece == null) {
            return null;
        }
        /// A move is valid if it is a "piece move" for the piece at the input location and making that move would not leave the team’s king in danger of check.
        // need isInCheck
        teamColor = piece.getTeamColor();
        validMoves = piece.pieceMoves(board, startPosition);
        for (ChessMove move : validMoves) {
            fakeBoard = new ChessBoard(board);
            fakeBoard.addPiece(move.getEndPosition(), piece);
            fakeBoard.removePiece(move.getStartPosition());
            moveWillCheck = isInCheckFuture(fakeBoard, teamColor);
            if (moveWillCheck) {
                removeMoves.add(move);
            }
        }
        validMoves.removeAll(removeMoves);
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        boolean isValid = false;
        board = getBoard();
        piece = board.getPiece(move.getStartPosition());
        validMoves = piece.pieceMoves(board, move.getStartPosition());
        if (validMoves.contains(move)) {
            isValid = true;
        }
        /// If the move is illegal (not valid or corresponding team color), it throws an InvalidMoveException.
        if (!isValid) {
            throw new InvalidMoveException();
        }
        /// Receives a given move and executes it, provided it is a legal move.
        board.addPiece(move.getEndPosition(), piece);
        board.removePiece(move.getStartPosition());

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
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
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
        // are in check and no valid moves for team
        Collection<ChessPiece> thisTeam;
        thisTeam = getTeamPieces(teamColor);
        ChessPosition theKingPosition = getKingPosition(teamColor);
        Collection<ChessMove> kingMoves;
        boolean moveWillCheck = false;
        if (isInCheck(teamColor)) {
            for (ChessPiece piece : thisTeam) {
                if (piece.getPieceType() == king) {
                    kingMoves = piece.pieceMoves(board, theKingPosition);
                    for (ChessMove move : kingMoves) {
                        moveWillCheck = checkFutureKingMovesWillCheck(move, teamColor);
                        if (!moveWillCheck) {
                            break;
                        }
                    }
                    if (moveWillCheck) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having no valid moves
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        /// Returns true if the given team has no legal moves but their king is not in immediate danger.
        ChessPiece isPiece;
        board = getBoard();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                isPiece = board.getPiece(new ChessPosition(i,j));
                if (isPiece != null) {
                    validMoves = isPiece.pieceMoves(board, new ChessPosition(i,j));
                    if (validMoves.isEmpty() && !isInCheck(teamColor) && !isInCheckmate(teamColor)) {
                        return true;
                    }
                }
            }
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

    private Collection<ChessPiece> getTeamPieces(TeamColor teamColor) {
        board = getBoard();
        ChessPiece isPiece;
        Collection<ChessPiece> thisTeam = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                isPiece = board.getPiece(new ChessPosition(i,j));
                if (isPiece != null) {
                    if (isPiece.getTeamColor() == teamColor) {
                        thisTeam.add(isPiece);
                    }
                }
            }
        }
        return thisTeam;
    }

    private ChessPosition getKingPosition(TeamColor teamColor) {
        board = getBoard();
        ChessPiece isPiece;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                isPiece = board.getPiece(new ChessPosition(i,j));
                if (isPiece != null) {
                    if (isPiece.getPieceType() == king && isPiece.getTeamColor() == teamColor) {
                        return new ChessPosition(i,j);
                    }
                }
            }
        }
        return null;
    }

    private boolean checkFutureKingMovesWillCheck(ChessMove move, TeamColor teamColor) {
        ChessBoard fakeBoard = new ChessBoard(board);
        ChessPiece king = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
        fakeBoard.addPiece(move.getEndPosition(), king);
        fakeBoard.removePiece(move.getStartPosition());
        if (isInCheckFuture(fakeBoard, teamColor)) {
            return true;
        }
        return false;
    }

    private boolean isInCheckFuture(ChessBoard fakeBoard, TeamColor teamColor) {
        /// Returns true if the specified team’s King could be captured by an opposing piece.
        ChessPiece isPiece;
        ChessPiece landingPiece;
        Collection<ChessMove> checkMoves;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                isPiece = fakeBoard.getPiece(new ChessPosition(i,j));
                if (isPiece != null) {
                    checkMoves = isPiece.pieceMoves(fakeBoard, new ChessPosition(i,j));
                    for (ChessMove validMove : checkMoves) {
                        landingPiece = fakeBoard.getPiece(validMove.getEndPosition());
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
}
