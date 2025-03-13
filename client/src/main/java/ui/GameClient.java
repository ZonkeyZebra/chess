package ui;

public class GameClient {
    private final ServerFacade server;
    private final String serverUrl;

    public GameClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }
}
