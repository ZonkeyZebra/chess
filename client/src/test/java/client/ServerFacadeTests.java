package client;

import dataaccess.DataAccessException;
import model.LoginRequest;
import model.RegisterRequest;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:8080");
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerTest() throws DataAccessException {
        var result = facade.register(new RegisterRequest("player1", "password", "p1@email.com"));
        Assertions.assertTrue(result.authToken().length() > 10);
    }

    @Test
    public void loginTest() throws DataAccessException {
        var result = facade.login(new LoginRequest("player1", "password"));
        Assertions.assertTrue(result.authToken().length() > 10);
    }

    @Test
    public void logoutTest() throws DataAccessException {
        var loginResult = facade.login(new LoginRequest("player1", "password"));
        facade.logout();
        Assertions.assertNull(loginResult.authToken());
    }

    @Test
    public void joinGameTest() {

    }

    @Test
    public void listGamesTest() {

    }

    @Test
    public void createGameTest() {

    }

}
