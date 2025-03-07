package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.ListGamesResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ListGamesServiceTest {
    AuthDAO auths = new MemoryAuthDAO();
    GameDAO games = new MemoryGameDAO();
    ListGamesService listGamesService = new ListGamesService(games, auths);
    Collection<GameData> result;
    AuthData authData;

    @Test
    void getGames() throws DataAccessException {
        auths.setAuthData(new AuthData("authToken", "username"));
        authData = auths.getAuth("authToken");
        games.createGame("awesome");
        result = listGamesService.getGames(authData.authToken());
        System.out.println(result);
        Assertions.assertNotNull(result);
    }

    @Test
    void getGamesFail() throws DataAccessException {
        auths.setAuthData(new AuthData(null, "username"));
        authData = auths.getAuth(null);
        Assertions.assertNull(authData.authToken());
    }
}