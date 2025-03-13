package ui;

public class PostLoginClient {
    private final ServerFacade server;
    private final String serverUrl;

    public PostLoginClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        return null;
    }

    public String logout() {
        return null;
    }

    public String createGame() {
        return null;
    }

    public String listGames() {
        return null;
    }

    public String playGame() {
        return null;
    }

    public String observeGame() {
        return null;
    }

    public String help() {
        return null;
    }
}
