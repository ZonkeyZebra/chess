package server.websocket;

import chess.*;
import dataaccess.*;
import exception.DataAccessException;
import model.AuthData;
import com.google.gson.Gson;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@WebSocket
public class WebSocketHandler {
    private final WebSocketSessions connections = new WebSocketSessions();
    private final AuthDAO authDAO = new MySqlAuthDAO();
    private final GameDAO gameDAO = new MySqlGameDAO();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        try {
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
            MakeMoveCommand moveCommand = new Gson().fromJson(message, MakeMoveCommand.class);

            AuthData authData = authDAO.getAuth(command.getAuthToken());
            String username = null;
            String authToken = command.getAuthToken();
            if (authData != null) {
                username = authData.username();
                saveSession(command.getGameID(), session, username);
            }

            switch (command.getCommandType()) {
                case CONNECT -> connect(command.getGameID(), session, username, authToken);
                case MAKE_MOVE -> makeMove(command.getGameID(), username, moveCommand, authToken);
                case LEAVE -> leaveGame(command.getGameID(), session, username);
                case RESIGN -> resignGame(command.getGameID(), session, username, authToken);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void connect(int gameID, Session session, String username, String authToken) throws IOException, DataAccessException {
        GameData game = gameDAO.getGame(gameID);
        if (authToken == null || authDAO.getAuth(authToken) == null) {
            connections.addSession(gameID, session, "");
            String message = "Bad auth. Please register or sign in.";
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message);
            connections.broadcastToUser(errorMessage, "", gameID);
            connections.removeSessionFromGame(gameID, session);
        } else if (game.game() == null) {
            connections.addSession(gameID, session, username);
            String message = "Not a valid game.";
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message);
            connections.broadcastToUser(errorMessage, username, gameID);
            connections.removeSessionFromGame(gameID, session);
        } else {
            connections.addSession(gameID, session, username);
            NotificationMessage notificationMessage = getNotificationMessage(username, game);
            LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, gameDAO.getGame(gameID).game());
            connections.broadcast(notificationMessage, username, gameID);
            connections.broadcastToUser(loadGameMessage, username, gameID);
        }
    }

    private static NotificationMessage getNotificationMessage(String username, GameData game) {
        String message = String.format("%s has joined the game as observer.", username);
        if (Objects.equals(username, game.whiteUsername())) {
            message = String.format("%s has joined the game as white.", username);
        }
        if (Objects.equals(username, game.blackUsername())) {
            message = String.format("%s has joined the game as black.", username);
        }
        return new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
    }

    public void makeMove(int gameID, String username, MakeMoveCommand command,
                         String authToken) throws IOException, DataAccessException, InvalidMoveException {
        ChessPosition endMove = command.getMove().getEndPosition();
        String move = String.format(convertColtoString(endMove.getColumn()) + convertRowtoString(endMove.getRow()));
        String message = String.format("%s made a move to %s.", username, move);

        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, gameDAO.getGame(gameID).game());
        String whiteUser = gameDAO.getGame(gameID).whiteUsername();
        String blackUser = gameDAO.getGame(gameID).blackUsername();
        ChessGame.TeamColor currentTeam = gameDAO.getGame(gameID).game().getTeamTurn();
        ChessPiece piece = gameDAO.getGame(gameID).game().getBoard().getPiece(command.getMove().getStartPosition());
        if (piece == null) {
            message = "Need to select a chess piece to move.";
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message);
            connections.broadcastToUser(errorMessage, username, gameID);
        }
        if (authToken == null || authDAO.getAuth(authToken) == null) {
            message = "Bad auth. Please register or sign in.";
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message);
            if (currentTeam == ChessGame.TeamColor.WHITE) {
                connections.broadcastToUser(errorMessage, whiteUser, gameID);
            } else {
                connections.broadcastToUser(errorMessage, blackUser, gameID);
            }
        } else {
            Collection<ChessMove> validMoves = gameDAO.getGame(gameID).game().validMoves(command.getMove().getStartPosition());
            if (validMoves.contains(command.getMove())) {
                if (currentTeam == ChessGame.TeamColor.WHITE) {
                    decideMessageToBroadcast(username, blackUser, whiteUser, notificationMessage, loadGameMessage, gameID, command.getMove());
                } else {
                    decideMessageToBroadcast(username, whiteUser, blackUser, notificationMessage, loadGameMessage, gameID, command.getMove());
                }
            } else {
                message = String.format("%s is not a valid move.", move);
                ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message);
                connections.broadcastToUser(errorMessage, username, gameID);
            }
        }
    }

    private void decideMessageToBroadcast(String username, String oppositeUser, String thisUser,
                                          NotificationMessage notificationMessage, LoadGameMessage loadGameMessage,
                                          int gameID, ChessMove move) throws IOException, DataAccessException, InvalidMoveException {
        String message;
        if (Objects.equals(username, oppositeUser)) {
            message = "Not your turn!";
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message);
            connections.broadcastToUser(errorMessage, username, gameID);
        } else if (!Objects.equals(username, thisUser) && !Objects.equals(username, oppositeUser)) {
            message = "You are only observing!";
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message);
            connections.broadcastToUser(errorMessage, username, gameID);
        } else if (gameDAO.getGame(gameID).game().getGameComplete()){
            message = "Game has been completed.";
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message);
            connections.broadcastToUser(errorMessage, username, gameID);
        } else {
            broadcastMove(username, notificationMessage, loadGameMessage, thisUser, move, gameID);
        }
    }

    private void broadcastMove(String username, NotificationMessage notificationMessage, LoadGameMessage loadGameMessage,
                               String teamUser, ChessMove move, int gameID) throws IOException, DataAccessException, InvalidMoveException {
        GameData game = gameDAO.getGame(gameID);
        game.game().makeMove(move);
        gameDAO.updateGame(new GameData(gameID, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game()));
        ChessGame.TeamColor currentTeam = game.game().getTeamTurn();
        loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, gameDAO.getGame(gameID).game());
        if (Objects.equals(username, teamUser)) {
            connections.broadcastToUser(loadGameMessage, username, gameID);
            connections.broadcast(loadGameMessage, username, gameID);
            connections.broadcast(notificationMessage, username, gameID);
            broadcastIfInCheck(username, gameID, game, currentTeam);
        } else {
            connections.broadcastToUser(loadGameMessage, username, gameID);
            connections.broadcast(loadGameMessage, username, gameID);
            connections.broadcastToUser(notificationMessage, username, gameID);
            broadcastIfInCheck(username, gameID, game, currentTeam);
        }
    }

    private void broadcastIfInCheck(String username, int gameID, GameData game, ChessGame.TeamColor currentTeam) throws IOException {
        if (game.game().isInCheckmate(currentTeam)) {
            String stateMessage = String.format("%s is in checkmate!", currentTeam);
            NotificationMessage checkmateMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, stateMessage);
            connections.broadcast(checkmateMessage, username, gameID);
            connections.broadcastToUser(checkmateMessage, username, gameID);
        }
        if (game.game().isInCheck(currentTeam)) {
            String stateMessage = String.format("%s is in check!", currentTeam);
            NotificationMessage checkmateMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, stateMessage);
            connections.broadcast(checkmateMessage, username, gameID);
            connections.broadcastToUser(checkmateMessage, username, gameID);
        }
        if (game.game().isInStalemate(currentTeam)) {
            String stateMessage = String.format("%s is in stalemate!", currentTeam);
            NotificationMessage checkmateMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, stateMessage);
            connections.broadcast(checkmateMessage, username, gameID);
            connections.broadcastToUser(checkmateMessage, username, gameID);
        }
    }

    public void leaveGame(int gameID, Session session, String username) throws IOException, DataAccessException {
        String message = String.format("%s left the game.", username);
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        GameData game = gameDAO.getGame(gameID);
        if (Objects.equals(username, game.whiteUsername())) {
            gameDAO.updateGame(new GameData(gameID, "", game.blackUsername(), game.gameName(), game.game()));
        }
        if (Objects.equals(username, game.blackUsername())) {
            gameDAO.updateGame(new GameData(gameID, game.whiteUsername(), "", game.gameName(), game.game()));
        }
        connections.broadcast(notificationMessage, username, gameID);
        connections.removeSessionFromGame(gameID, session);
    }

    public void resignGame(int gameID, Session session, String username, String authToken) throws IOException, DataAccessException {
        String message = String.format("%s forfeited the game.", username);
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        String whiteUser = gameDAO.getGame(gameID).whiteUsername();
        String blackUser = gameDAO.getGame(gameID).blackUsername();
        if (!Objects.equals(username, whiteUser) && !Objects.equals(username, blackUser)) {
            message = "You are only observing! If you want to exit the game please type 'leave'.";
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message);
            connections.broadcastToUser(errorMessage, username, gameID);
        } else {
            GameData gameData = gameDAO.getGame(gameID);
            ChessGame game = gameData.game();
            if (game.getGameComplete()) {
                message = "Game has been completed.";
                ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message);
                connections.broadcastToUser(errorMessage, username, gameID);
            } else {
                game.setGameStatus(true);
                gameDAO.updateGame(new GameData(gameID, whiteUser, blackUser, gameData.gameName(), game));
                gameDAO.deleteSingleGame(gameID);
                connections.broadcast(notificationMessage, username, gameID);
                connections.broadcastToUser(notificationMessage, username, gameID);
                connections.removeSessionFromGame(gameID, session);
            }
        }
    }

    private void saveSession(int gameID, Session session, String username) {
        connections.addSession(gameID, session, username);
    }

    private String convertRowtoString(int row) {
        if (row == 1) {
            return "1";
        } else if (row == 2) {
            return "2";
        } else if (row == 3) {
            return "3";
        } else if (row == 4) {
            return "4";
        } else if (row == 5) {
            return "5";
        } else if (row == 6) {
            return "6";
        } else if (row == 7) {
            return "7";
        } else if (row == 8) {
            return "8";
        }
        return "0";
    }

    private String convertColtoString(int col) {
        if (col == 1) {
            return "a";
        } else if (col == 2) {
            return "b";
        } else if (col == 3) {
            return "c";
        } else if (col == 4) {
            return "d";
        } else if (col == 5) {
            return "e";
        } else if (col == 6) {
            return "f";
        } else if (col == 7) {
            return "g";
        } else if (col == 8) {
            return "h";
        }
        return "0";
    }
}
