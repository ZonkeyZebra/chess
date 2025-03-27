package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketConnections {
    private final ConcurrentHashMap<Integer, Connection> connections = new ConcurrentHashMap<>();

    public void addConnection(int gameID, Session session) {
        var connection = new Connection(gameID, session);
        connections.put(gameID, connection);
    }

    public void removeConnection(int gameID, Session session) {
        connections.remove(gameID);
    }

    public Connection getConnection(int gameID) {
        return connections.get(gameID);
    }

    public void broadcast(String excludeSession, ServerMessage notification, String message) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var connection : connections.values()) {
            if (connection.session.isOpen()) {
                if (!connection.session.toString().equals(excludeSession)) {
                    connection.send(String.format(message, notification));
                }
            } else {
                removeList.add(connection);
            }
        }

        // Clean up any connections that were left open.
        for (var connection : removeList) {
            connections.remove(connection.gameID);
        }
    }
}
