package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSessions {
    private final ConcurrentHashMap<Integer, Set<Session>> gameMap;
    private final ConcurrentHashMap<Session, String> userSession; //use this like connection
    private final ConcurrentHashMap<Session, Integer> gameSession;
    private final Set<Session> sessions;
    private ArrayList<Session> removeList;
    private Set<Session> addList = new HashSet<>();

    public WebSocketSessions() {
        this.sessions = new HashSet<>();
        this.gameMap = new ConcurrentHashMap<>();
        this.userSession = new ConcurrentHashMap<>();
        this.gameSession = new ConcurrentHashMap<>();
    }

    public void addSession(int gameID, Session session, String username) {
        sessions.add(session);
        userSession.put(session, username);
        gameSession.put(session, gameID);
        addList.clear();
        addList = getSessionsToAdd(gameID);
        gameMap.put(gameID, addList);
    }

    private Set<Session> getSessionsToAdd(int gameID) {
        for (Session session : sessions) {
            if (gameSession.get(session) == gameID) {
                addList.add(session);
            }
        }
        return addList;
    }

    public void removeSessionFromGame(int gameID, Session session) {
        gameMap.remove(gameID);
        sessions.remove(session);
        userSession.remove(session);
        gameSession.remove(session);
        addList.clear();
        removeList.clear();
        session.close();
    }

    public void broadcast(ServerMessage serverMessage, String username, int gameID) throws IOException {
        removeList = new ArrayList<Session>();
        Set<Session> gameSessions = gameMap.get(gameID);
        for (Session session : gameSessions) {
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
            gameSession.remove(session);
        }
    }

    public void broadcastToUser(ServerMessage message, String username, int gameID) throws IOException {
        removeList = new ArrayList<Session>();
        Set<Session> gameSessions = gameMap.get(gameID);
        for (Session session : gameSessions) {
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
