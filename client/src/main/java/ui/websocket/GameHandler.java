package ui.websocket;

import websocket.messages.ServerMessage;

public interface GameHandler {
    void printMessage(String message);
    void notify(ServerMessage serverMessage);
}
