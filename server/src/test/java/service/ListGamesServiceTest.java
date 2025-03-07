package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.ListGamesResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ListGamesServiceTest {
    AuthDAO auths = new MySqlAuthDAO();
    GameDAO games = new MySqlGameDAO();
    ListGamesService listGamesService = new ListGamesService(games, auths);
    Collection<GameData> result;
    AuthData authData;

    @BeforeEach
    void setup() throws DataAccessException {
        auths.deleteAllAuths();
        games.deleteGame();
    }

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
        Assertions.assertThrows(DataAccessException.class, () -> listGamesService.getGames(null));
    }
}