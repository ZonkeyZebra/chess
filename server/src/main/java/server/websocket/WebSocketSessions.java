package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSessions {
    private final ConcurrentHashMap<Integer, Set<Session>> gameMap;
    private final ConcurrentHashMap<Session, Integer> sessionMap;
    private final Set<Session> sessions;

    public WebSocketSessions() {
        this.sessions = new HashSet<>();
        this.gameMap = new ConcurrentHashMap<>();
        this.sessionMap = new ConcurrentHashMap<>();
    }

    public void addSession(int gameID, Session session) {
        sessions.add(session);
        gameMap.put(gameID, sessions);
        sessionMap.put(session, gameID);
    }

    public void removeSessionFromGame(int gameID, Session session) {
        gameMap.remove(gameID);
    }

    public void removeSession(Session session) {
        sessionMap.remove(session);
    }

    public Set<Session> getSessionsForGame(int gameID) {
        return gameMap.get(gameID);
    }

    public void broadcast(String excludeSession, int gameID, String message, NotificationMessage serverMessage) throws IOException {
        for (Session session : sessions) {
            if (session.isOpen()) {
                if (!Objects.equals(session.toString(), excludeSession)) {
                    session.getRemote().sendString(new Gson().toJson(serverMessage));
                }
            }
        }
    }

    public void broadcastGame(LoadGameMessage loadGameMessage) throws IOException {
        for (Session session : sessions) {
            if (session.isOpen()) {
                session.getRemote().sendString(new Gson().toJson(loadGameMessage));
            }
        }
    }
}
