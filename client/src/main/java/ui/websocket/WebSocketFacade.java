package ui.websocket;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import chess.ChessMove;
import com.google.gson.Gson;
import exception.DataAccessException;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {
    Session session;
    GameHandler gameHandler;

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        //Endpoint requires this method, but you don't have to do anything
    }

    public WebSocketFacade(String url, GameHandler handler) throws Exception {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.gameHandler = handler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {

                @Override
                public void onMessage(String message) {
                    try {
                        ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                        NotificationMessage notificationMessage = new Gson().fromJson(message, NotificationMessage.class);
                        LoadGameMessage loadGameMessage = new Gson().fromJson(message, LoadGameMessage.class);
                        ErrorMessage errorMessage = new Gson().fromJson(message, ErrorMessage.class);
                        switch (serverMessage.getServerMessageType()) {
                            case NOTIFICATION -> gameHandler.notify(notificationMessage);
                            case LOAD_GAME -> gameHandler.notify(loadGameMessage);
                            case ERROR -> gameHandler.notify(errorMessage);
                            default -> gameHandler.notify(serverMessage);
                        }
                        //gameHandler.notify(serverMessage);
                    } catch(Exception ex) {
                        gameHandler.printMessage(ex.getMessage()); //?
                    }
                }

            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public void connect(int gameID, String authToken) throws DataAccessException {
        try {
            UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void makeMove(int gameID, String authToken, ChessMove move) throws DataAccessException {
        try {
            //UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.MAKE_MOVE, authToken, gameID);
            MakeMoveCommand command = new MakeMoveCommand(UserGameCommand.CommandType.MAKE_MOVE, authToken, gameID, move);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void leaveGame(int gameID, String authToken) throws DataAccessException {
        try {
            UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
            this.session.close();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void resignGame(int gameID, String authToken) throws DataAccessException {
        try {
            UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.RESIGN, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
            this.session.close();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private void sendMessage() throws IOException {
        // create command message
        String msg = null;
        // send message to server
        this.session.getBasicRemote().sendText(msg);
    }
}
