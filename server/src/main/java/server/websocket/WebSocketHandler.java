package server.websocket;

import dataaccess.AuthDAO;
import exception.DataAccessException;
import model.AuthData;
import org.eclipse.jetty.websocket.api.annotations.*;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.common.WebSocketSession;
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
            switch (command.getCommandType()) {
                case CONNECT -> connect(command.getGameID(), session, username);
                case MAKE_MOVE -> makeMove(command.getGameID(), session, username);
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

    public void connect(int gameID, Session session, String username) throws IOException {
        connections.addSession(gameID, session);
        String message = String.format("%s has joined the game.", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        broadcast(session.toString(), notification, message);
    }

    public void makeMove(int gameID, Session session, String username) throws IOException {
        String message = String.format("%s made a move.", username);
        ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        broadcast(session.toString(), notification, message);
    }

    public void leaveGame(int gameID, Session session, String username) throws IOException {
        connections.removeSessionFromGame(gameID, session);
        String message = String.format("%s left the game.", username);
        ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        broadcast(session.toString(), notification, message);
    }

    public void resignGame(int gameID, Session session, String username) throws IOException {
        connections.removeSessionFromGame(gameID, session);
        String message = String.format("%s forfeited the game.", username);
        ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        broadcast(session.toString(), notification, message);
    }

    public void broadcast(String excludeSession, ServerMessage notification, String message) throws IOException {

    }
}
