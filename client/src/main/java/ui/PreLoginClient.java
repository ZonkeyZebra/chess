package ui;

public class PreLoginClient {
    private final ServerFacade server;
    private final String serverUrl;

    public PreLoginClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        return null;
    }

    public String login() {
        return null;
    }

    public String help() {
        return null;
    }
}
