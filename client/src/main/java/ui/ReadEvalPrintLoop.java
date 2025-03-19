package ui;

import chess.ChessGame;

import java.util.Objects;
import java.util.Scanner;

public class ReadEvalPrintLoop {
    private final PreLoginClient preLoginClient;
    private final PostLoginClient postLoginClient;
    private boolean loginStatus = false;
    private String authToken;

    public ReadEvalPrintLoop(String serverUrl) {
        preLoginClient = new PreLoginClient(serverUrl);
        postLoginClient = new PostLoginClient(serverUrl);
    }

    public void run() {
        System.out.println("Welcome to Chess. Sign in or register to start.");
        System.out.println(preLoginClient.help());

        Scanner scanner = new Scanner(System.in);
        String result = "";
        while (!Objects.equals(result, "quit")) {
            printPrompt();
            String line = scanner.nextLine();
            result = getResult(result, line);
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
                    new DrawBoard(ChessGame.TeamColor.WHITE); // in phase 6 this will go to GameClient
                }
                if (result.contains("Draw Board: BLACK")) {
                    new DrawBoard(ChessGame.TeamColor.BLACK); // in phase 6 this will go to GameClient
                }
            } else {
                result = preLoginClient.eval(line);
                setSignIn(true);
            }
            if (!result.contains("Draw Board: observe") && !result.contains("Draw Board: WHITE") && !result.contains("Draw Board: BLACK")) {
                System.out.print("\u001B[34m" + result);
            }
        } catch (Throwable e) {
            var msg = e.toString();
            System.out.print(msg);
        }
        return result;
    }

    private void printPrompt() {
        System.out.print("\n" + "\u001B[0m" + ">>> " + "\u001B[32m");
    }

    private void setSignIn(boolean bool) {
        loginStatus = bool;
    }
}
