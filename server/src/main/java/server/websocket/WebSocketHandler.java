package server.websocket;

import dataaccess.AuthDAO;
import exception.DataAccessException;
import model.AuthData;
import org.eclipse.jetty.websocket.api.annotations.*;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    private final WebSocketSessions connections = new WebSocketSessions();
    private AuthDAO authDAO;

    @OnWebSocketMessage
    public void onMessage(WebSocketSession session, String message) throws DataAccessException, IOException {
        try {
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

            AuthData authData = authDAO.getAuth(command.getAuthToken());
            String username = authData.username();

            saveSession(command.getGameID(), session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(command.getGameID(), session, username);
                case MAKE_MOVE -> makeMove(command.getGameID(), session, username, ((MakeMoveCommand) command));
                case LEAVE -> leaveGame(command.getGameID(), session, username);
                case RESIGN -> resignGame(command.getGameID(), session, username);
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    @OnWebSocketError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Connected to session: " + session);
    }

    @OnWebSocketClose
    public void onClose(Session session) {
        System.out.println("Closed session: " + session);
    }

    public void connect(int gameID, Session session, String username) throws IOException {
        connections.addSession(gameID, session);
        String message = String.format("%s has joined the game.", username);
        broadcast(session.toString(), gameID, message);
    }

    public void makeMove(int gameID, Session session, String username, MakeMoveCommand command) throws IOException {
        String message = String.format("%s made a move to %s.", username, command.getMove());
        broadcast(session.toString(), gameID, message);
    }

    public void leaveGame(int gameID, Session session, String username) throws IOException {
        connections.removeSessionFromGame(gameID, session);
        String message = String.format("%s left the game.", username);
        broadcast(session.toString(), gameID, message);
    }

    public void resignGame(int gameID, Session session, String username) throws IOException {
        connections.removeSessionFromGame(gameID, session);
        String message = String.format("%s forfeited the game.", username);
        broadcast(session.toString(), gameID, message);
    }

    public void sendMessage(Session session) {

    }

    public void broadcast(String excludeSession, int gameID, String message) throws IOException {

    }

    private void saveSession(int gameID, Session session) {
        connections.addSession(gameID, session);
    }
}
