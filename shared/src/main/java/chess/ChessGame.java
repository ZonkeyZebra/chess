package chess;

import java.util.ArrayList;
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
    private boolean gameComplete;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        setBoard(board);
        setTeamTurn(TeamColor.WHITE);
        setGameStatus(false);
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
        Collection<ChessMove> removeMoves = new ArrayList<>();
        boolean moveWillCheck;
        TeamColor teamColor;
        ChessBoard fakeBoard;

        /// If there is no piece at that location, this method returns null
        if (piece == null) {
            return null;
        }
        /// A move is valid if it's a "piece move" for the piece and making that move would not leave the team’s king in danger.
        // need isInCheck
        teamColor = piece.getTeamColor();
        validMoves = piece.pieceMoves(board, startPosition);
        for (ChessMove move : validMoves) {
            fakeBoard = new ChessBoard(board);
            ChessPosition endPosition = move.getEndPosition();
            if (endPosition.getColumn() >= 1 && endPosition.getRow() >= 1 && endPosition.getRow() <= 8 && endPosition.getColumn() <= 8) {
                if (fakeBoard.getPiece(move.getEndPosition()) != null) {
                    fakeBoard.removePiece(move.getEndPosition());
                }
                fakeBoard.addPiece(move.getEndPosition(), piece);
                fakeBoard.removePiece(move.getStartPosition());
                moveWillCheck = isInCheckFuture(fakeBoard, teamColor);
                if (moveWillCheck) {
                    removeMoves.add(move);
                }
            } else {
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
        if (piece == null) {
            throw new InvalidMoveException();
        }
        validMoves = validMoves(move.getStartPosition());
        if (validMoves.contains(move)) {
            isValid = true;
        }
        /// If the move is illegal (not valid or corresponding team color), it throws an InvalidMoveException.
        if (!isValid) {
            throw new InvalidMoveException("Not valid move.");
        }
        if (piece.getTeamColor() != getTeamTurn()) {
            throw new InvalidMoveException("Not your turn!");
        }
        /// Receives a given move and executes it, provided it is a legal move.
        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            if (move.getPromotionPiece() != null) {
                board.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), move.getPromotionPiece()));
                board.removePiece(move.getStartPosition());
            } else {
                board.addPiece(move.getEndPosition(), piece);
                board.removePiece(move.getStartPosition());
            }
        } else {
            board.addPiece(move.getEndPosition(), piece);
            board.removePiece(move.getStartPosition());
        }
        TeamColor currentTeam = getTeamTurn();
        TeamColor nextTeam = getOppositeTeamColor(currentTeam);
        setTeamTurn(nextTeam);

    }


    /**
     * Determines if the given team is in check
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        /// Returns true if the specified team’s King could be captured by an opposing piece.
        board = getBoard();
        if(checkBoard(board, teamColor)) {
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        /// Returns true if the given team has no way to protect their king from being captured. Is in check and no valid moves for team
        Collection<ChessPiece> thisTeam;
        thisTeam = getTeamPieces(teamColor);
        Collection<Boolean> wasEmpty = new ArrayList<>();

        if (isInCheck(teamColor)) {
            if (checkNoValidMoves(teamColor, thisTeam, wasEmpty)) {
                return true;
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
        Collection<ChessPiece> thisTeam;
        thisTeam = getTeamPieces(teamColor);
        Collection<Boolean> wasEmpty = new ArrayList<>();

        if (!isInCheck(teamColor)) {
            if (checkNoValidMoves(teamColor, thisTeam, wasEmpty)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkNoValidMoves(TeamColor teamColor, Collection<ChessPiece> thisTeam, Collection<Boolean> wasEmpty) {
        ChessPosition thePiecePosition;
        for (ChessPiece piece : thisTeam) {
            thePiecePosition = getPiecePosition(teamColor, piece.getPieceType());
            validMoves = validMoves(thePiecePosition);
            if (validMoves.isEmpty()) {
                wasEmpty.add(true);
            }
        }
        if (wasEmpty.size() == thisTeam.size()) {
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

    private ChessPosition getPiecePosition(TeamColor teamColor, ChessPiece.PieceType pieceType) {
        board = getBoard();
        ChessPiece isPiece;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                isPiece = board.getPiece(new ChessPosition(i,j));
                if (isPiece != null) {
                    if (isPiece.getPieceType() == pieceType && isPiece.getTeamColor() == teamColor) {
                        return new ChessPosition(i,j);
                    }
                }
            }
        }
        return null;
    }

    private boolean isInCheckFuture(ChessBoard fakeBoard, TeamColor teamColor) {
        /// Returns true if the specified team’s King could be captured by an opposing piece.
        if (checkBoard(fakeBoard, teamColor)) {
            return true;
        }
        return false;
    }

    private TeamColor getOppositeTeamColor(TeamColor currentTeam) {
        if (currentTeam == TeamColor.BLACK) {
            return TeamColor.WHITE;
        }
        return TeamColor.BLACK;
    }

    private boolean checkBoard(ChessBoard theBoard, TeamColor teamColor) {
        ChessPiece isPiece;
        ChessPiece landingPiece;
        Collection<ChessMove> checkMoves;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                isPiece = theBoard.getPiece(new ChessPosition(i,j));
                if (isPiece != null) {
                    checkMoves = isPiece.pieceMoves(theBoard, new ChessPosition(i,j));
                    if (checkFutureMoves(theBoard, teamColor, checkMoves)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkFutureMoves(ChessBoard theBoard, TeamColor teamColor, Collection<ChessMove> checkMoves) {
        for (ChessMove validMove : checkMoves) {
            ChessPiece landingPiece = theBoard.getPiece(validMove.getEndPosition());
            if (landingPiece != null) {
                if (landingPiece.getPieceType() == king && landingPiece.getTeamColor() == teamColor) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setGameStatus(boolean bool) {
        this.gameComplete = bool;
    }

    public boolean getGameComplete() {
        return gameComplete;
    }
}
