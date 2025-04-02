package ui.websocket;

import chess.ChessGame;
import websocket.messages.ServerMessage;

public interface GameHandler {
    void updateGame(ChessGame game);
    void printMessage(String message);
    void notify(ServerMessage serverMessage);
}
