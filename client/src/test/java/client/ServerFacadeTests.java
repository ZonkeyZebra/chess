package client;

import chess.ChessGame;
import exception.DataAccessException;
import model.CreateGameRequest;
import model.JoinGameRequest;
import model.LoginRequest;
import model.RegisterRequest;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    private static final String BAD_AUTH = "";

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        facade.clear();
        facade.register(new RegisterRequest("new", "new", "new@email.com"));
    }


    @Test
    public void registerTest() throws DataAccessException {
        var result = facade.register(new RegisterRequest("player1", "password", "p1@email.com"));
        Assertions.assertTrue(result.authToken().length() > 10);
    }

    @Test
    public void registerFail() {
        Assertions.assertThrows(DataAccessException.class, () -> facade.register(new RegisterRequest("player1", null, "p1@email.com")));
    }

    @Test
    public void loginTest() throws DataAccessException {
        var result = facade.login(new LoginRequest("new", "new"));
        Assertions.assertTrue(result.authToken().length() > 10);
    }

    @Test
    public void loginFail() {
        Assertions.assertThrows(DataAccessException.class, () -> facade.login(new LoginRequest("new", null)));
    }

    @Test
    public void logoutTest() throws DataAccessException {
        var loginResult = facade.login(new LoginRequest("new", "new"));
        String authToken = loginResult.authToken();
        Assertions.assertAll(() -> facade.logout(authToken));
    }

    @Test
    public void logoutFail() {
        Assertions.assertThrows(DataAccessException.class, () -> facade.logout(BAD_AUTH));
    }

    @Test
    public void joinGameTest() throws DataAccessException {
        var loginResult = facade.login(new LoginRequest("new", "new"));
        String authToken = loginResult.authToken();
        facade.createGame(new CreateGameRequest("awesome"), authToken);
        Assertions.assertAll(() -> facade.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, 1), authToken));
    }

    @Test
    public void joinGameFail() throws DataAccessException {
        var loginResult = facade.login(new LoginRequest("new", "new"));
        String authToken = loginResult.authToken();
        facade.createGame(new CreateGameRequest("awesome"), authToken);
        Assertions.assertThrows(DataAccessException.class, () -> facade.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, 1), BAD_AUTH));
    }

    @Test
    public void listGamesTest() throws DataAccessException {
        var loginResult = facade.login(new LoginRequest("new", "new"));
        String authToken = loginResult.authToken();
        var listResult = facade.listGames(authToken);
        Assertions.assertNotNull(listResult);
    }

    @Test
    public void listFail() {
        Assertions.assertThrows(DataAccessException.class, () -> facade.listGames(BAD_AUTH));
    }

    @Test
    public void createGameTest() throws DataAccessException {
        var loginResult = facade.login(new LoginRequest("new", "new"));
        String authToken = loginResult.authToken();
        var createResult = facade.createGame(new CreateGameRequest("another"), authToken);
        Assertions.assertNotNull(createResult);
    }

    @Test
    public void createGameFail() {
        Assertions.assertThrows(DataAccessException.class, () -> facade.createGame(new CreateGameRequest("another"), BAD_AUTH));
    }

    @Test
    public void clearTest() {
        Assertions.assertAll(() -> facade.clear());
    }

    @Test
    public void clearFail() throws DataAccessException {
        var loginResult = facade.login(new LoginRequest("new", "new"));
        Assertions.assertNotNull(loginResult);
    }

}
