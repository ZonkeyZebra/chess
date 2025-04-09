package ui;

import chess.ChessGame;
import ui.websocket.GameHandler;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.util.Objects;
import java.util.Scanner;

public class ReadEvalPrintLoop implements GameHandler {
    private final PreLoginClient preLoginClient;
    private final PostLoginClient postLoginClient;
    private final GameClient gameClient;
    private boolean loginStatus = false;
    private String authToken;
    private boolean inGame = false;
    private boolean observer = false;
    private ChessGame.TeamColor teamColor = ChessGame.TeamColor.WHITE;
    ChessGame mostRecentGame;


    public ReadEvalPrintLoop(String serverUrl) {
        preLoginClient = new PreLoginClient(serverUrl);
        postLoginClient = new PostLoginClient(serverUrl, this);
        gameClient = new GameClient(serverUrl, this);
    }

    public void run() {
        System.out.println("Welcome to Chess. Sign in or register to start.");
        System.out.println(preLoginClient.help());

        Scanner scanner = new Scanner(System.in);
        String result = "";
        while (!Objects.equals(result, "quit")) {
            printPrompt();
            String line = scanner.nextLine();
            if (inGame) {
                result = getGameResult(result, line, preLoginClient.getAuthToken(), observer);
            } else {
                result = getResult(result, line);
            }
        }
        System.out.println();
    }

    private String getResult(String result, String line) {
        try {
            if (loginStatus) {
                authToken = preLoginClient.getAuthToken();
                result = postLoginClient.eval(line, authToken);
                if (line.contains("logout")) {
                    setSignIn(false);
                }
                if (result.contains("Draw Board: observe") || result.contains("Draw Board: WHITE")) {
                    teamColor = ChessGame.TeamColor.WHITE;
                    //ChessGame game = postLoginClient.getChessGame();
                    System.out.println("\n");
                    //new DrawBoard(teamColor, game.getBoard());
                    setInGame(true);
                    if (result.contains("observe")) {
                        setObserver(true);
                    }
                }
                if (result.contains("Draw Board: BLACK")) {
                    teamColor = ChessGame.TeamColor.BLACK;
                    //ChessGame game = postLoginClient.getChessGame();
                    System.out.println("\n");
                    //new DrawBoard(teamColor, game.getBoard());
                    setInGame(true);
                }
            } else {
                result = preLoginClient.eval(line);
                if (result.contains("Signed in") || result.contains("Registered as")) {
                    setSignIn(true);
                }
            }
            if (!result.contains("Draw Board: observe") && !result.contains("Draw Board: WHITE") && !result.contains("Draw Board: BLACK")) {
                System.out.print("\u001B[34m" + result);
            }
        } catch (Throwable e) {
            throwError(e);
        }
        return result;
    }

    private String getGameResult(String result, String line, String authToken, boolean isObserver) {
        try {
            //result = gameClient.eval(line, authToken, teamColor, postLoginClient.getChessGame(), postLoginClient.getGameNum(), isObserver);
            result = gameClient.eval(line, authToken, teamColor, mostRecentGame, postLoginClient.getGameNum(), isObserver);
            if (line.contains("leave") || line.contains("resign")) {
                setInGame(false);
                setObserver(false);
            }
            System.out.print("\u001B[34m" + result);
        } catch (Throwable e) {
            throwError(e);
        }
        return result;
    }

    private static void throwError(Throwable e) {
        var msg = e.toString();
        var error = msg.split(":");
        if (msg.contains("For input string")) {
            System.out.print(error[2] + " is not a valid input. Type help for more info.");
        } else {
            System.out.print(error[1]);
        }
    }

    private void printPrompt() {
        System.out.print("\n" + "\u001B[0m" + ">>> " + "\u001B[32m");
    }

    private void setSignIn(boolean bool) {
        loginStatus = bool;
    }

    private void setInGame(boolean bool) {
        inGame = bool;
    }

    private void setObserver(boolean bool) {
        observer = bool;
    }

    public void notify(ServerMessage message) {
        switch (message.getServerMessageType()) {
            case NOTIFICATION -> displayNotification(((NotificationMessage) message).getMessage());
            case ERROR -> displayError(((ErrorMessage) message).getErrorMessage());
            case LOAD_GAME -> loadGame(((LoadGameMessage) message).getGame());
        }
    }


    public void printMessage(String message) {
        System.out.println("\u001b[31m" + message);
        printPrompt();
    }

    private void displayNotification(String message) {
        System.out.println("\u001b[31m" + message);
        printPrompt();
    }

    private void displayError(String message) {
        System.out.println("\u001b[31m" + message);
        printPrompt();
    }

    private void loadGame(ChessGame game) {
        System.out.println("\n");
        mostRecentGame = game;
        new DrawBoard(teamColor, game.getBoard());
        printPrompt();
    }
}
