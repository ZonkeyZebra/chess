package service;

import dataaccess.*;
import model.AuthData;
import model.CreateGameRequest;
import model.CreateGameResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateGameServiceTest {
    AuthDAO auths = new MySqlAuthDAO();
    GameDAO games = new MySqlGameDAO();
    CreateGameService createGameService = new CreateGameService(games, auths);
    CreateGameRequest request;
    CreateGameResult result;
    AuthData authData;

    @Test
    void createGame() throws DataAccessException {
        auths.setAuthData(new AuthData("authToken", "username"));
        authData = auths.getAuth("authToken");
        request = new CreateGameRequest("awesome");
        result = createGameService.createGame(request, authData.authToken());
        System.out.println(result);
        Assertions.assertNotNull(result);
    }

    @Test
    void createGameFail() throws DataAccessException {
        auths.setAuthData(new AuthData(null, "username"));
        authData = auths.getAuth(null);
        request = new CreateGameRequest("awesome");
        result = createGameService.createGame(request, null);
        System.out.println(result);
        Assertions.assertNull(authData.authToken());
    }
}