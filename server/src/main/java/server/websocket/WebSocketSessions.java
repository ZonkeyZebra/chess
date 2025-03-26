package server.websocket;

import spark.Session;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSessions {
    private final ConcurrentHashMap<Integer, Set<Session>> sessions = new ConcurrentHashMap<>();

    public void addSessionToGame(int gameID, Session session) {
        //TODO
    }

    public void removeSessionFromGame(int gameID, Session session) {
        //TODO
    }

    public void removeSession(Session session) {
        //TODO
    }

    public Set<Session> getSessionsForGame(int gameID) {
        //TODO
        return null;
    }
}
