package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.MySqlAuthDAO;
import dataaccess.MySqlGameDAO;
import exception.DataAccessException;
import model.AuthData;
import org.eclipse.jetty.websocket.api.annotations.*;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    private final WebSocketSessions connections = new WebSocketSessions();
    private AuthDAO authDAO = new MySqlAuthDAO();
    private GameDAO gameDAO = new MySqlGameDAO();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        try {
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
            MakeMoveCommand moveCommand = new Gson().fromJson(message, MakeMoveCommand.class);

            AuthData authData = authDAO.getAuth(command.getAuthToken());
            String username = authData.username();

            saveSession(command.getGameID(), session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(command.getGameID(), session, username);
                case MAKE_MOVE -> makeMove(command.getGameID(), session, username, moveCommand);
                case LEAVE -> leaveGame(command.getGameID(), session, username);
                case RESIGN -> resignGame(command.getGameID(), session, username);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

//    @OnWebSocketError
//    public void onError(Session session, Throwable throwable) {
//        throwable.printStackTrace();
//        System.out.println("Error: " + throwable.getMessage());
//    }

    public void connect(int gameID, Session session, String username) throws IOException, DataAccessException {
        connections.addSession(gameID, session);
        String message = String.format("%s has joined the game.", username);
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        //LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, gameDAO.getGame(gameID).game());
        connections.broadcast(session, gameID, notificationMessage);
        //connections.broadcastGame(loadGameMessage);
    }

    public void makeMove(int gameID, Session session, String username, MakeMoveCommand command) throws IOException {
        ChessPosition endMove = command.getMove().getEndPosition();
        String move = String.format(convertColtoString(endMove.getColumn()) + convertRowtoString(endMove.getRow()));
        String message = String.format("%s made a move to %s.", username, move);
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, gameID, notificationMessage);
    }

    public void leaveGame(int gameID, Session session, String username) throws IOException {
        connections.removeSessionFromGame(gameID, session);
        String message = String.format("%s left the game.", username);
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, gameID, notificationMessage);
    }

    public void resignGame(int gameID, Session session, String username) throws IOException {
        connections.removeSessionFromGame(gameID, session);
        String message = String.format("%s forfeited the game.", username);
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, gameID, notificationMessage);
    }

    private void saveSession(int gameID, Session session) {
        connections.addSession(gameID, session);
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
