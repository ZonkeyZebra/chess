package ui;

import java.util.Objects;
import java.util.Scanner;

public class ReadEvalPrintLoop {
    private final PreLoginClient preLoginClient;
    private final PostLoginClient postLoginClient;
    private boolean loginStatus = false;

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
            try {
                if (loginStatus) {
                    result = postLoginClient.eval(line);
                    if (line.contains("logout")) {
                        setSignIn(false);
                    }
                } else {
                    result = preLoginClient.eval(line);
                    setSignIn(true);
                }
                System.out.print("\u001B[34m" + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + "\u001B[0m" + ">>> " + "\u001B[32m");
    }

    private void setSignIn(boolean bool) {
        loginStatus = bool;
    }
}
