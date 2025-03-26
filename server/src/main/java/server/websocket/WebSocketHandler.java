package server.websocket;

import org.eclipse.jetty.websocket.api.annotations.*;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.util.Timer;

public class WebSocketHandler {
    private WebSocketSessions sessions;

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case CONNECT -> connect(message);
            case MAKE_MOVE -> makeMove(message);
            case LEAVE -> leaveGame(message);
            case RESIGN -> resignGame(message);
        }
    }

    public void connect(String message) {
        //TODO
    }

    public void makeMove(String message) {
        //TODO
    }

    public void leaveGame(String message) {
        //TODO
    }

    public void resignGame(String message) {
        //TODO
    }

    public void sendMessage(String message, Session session) {
        //TODO
    }

    public void broadcastMessage(int gameID, String message, Session exceptThisSession) {
        //TODO
    }
}
