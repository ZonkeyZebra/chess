package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSessions {
    private final ConcurrentHashMap<Integer, Set<Session>> gameMap;
    private final ConcurrentHashMap<Set<Session>, Integer> sessionMap;
    private final ConcurrentHashMap<Session, String> userSession; //use this like connection
    private final Set<Session> sessions;
    private ArrayList<Session> removeList;

    public WebSocketSessions() {
        this.sessions = new HashSet<>();
        this.gameMap = new ConcurrentHashMap<>();
        this.sessionMap = new ConcurrentHashMap<>();
        this.userSession = new ConcurrentHashMap<>();
    }

    public void addSession(int gameID, Session session, String username) {
        sessions.add(session);
        gameMap.put(gameID, sessions);
        sessionMap.put(sessions, gameID);
        userSession.put(session, username);
    }

    public void removeSessionFromGame(int gameID, Session session) {
        gameMap.remove(gameID);
        sessions.remove(session);
        userSession.remove(session);
    }

    public void removeSession(Session session) {
        sessionMap.remove(sessions);
    }

    public Set<Session> getSessionsForGame(int gameID) {
        return gameMap.get(gameID);
    }

    public void broadcast(ServerMessage serverMessage, String username) throws IOException {
        removeList = new ArrayList<Session>();
        for (Session session : sessions) {
            if (session.isOpen()) {
                if (!Objects.equals(username, userSession.get(session))) {
                    session.getRemote().sendString(new Gson().toJson(serverMessage));
                }
            } else {
                removeList.add(session);
            }
        }
        cleanUpConnections(removeList);
    }

    private void cleanUpConnections(ArrayList<Session> removeList) {
        // Clean up any connections that were left open.
        for (var session : removeList) {
            sessions.remove(session);
        }
    }

    public void broadcastToUser(ServerMessage message, String username) throws IOException {
        removeList = new ArrayList<Session>();
        for (Session session : sessions) {
            if (session.isOpen()) {
                if (Objects.equals(username, userSession.get(session))) {
                    session.getRemote().sendString(new Gson().toJson(message));
                }
            } else {
                removeList.add(session);
            }
        }
        cleanUpConnections(removeList);
    }
}
