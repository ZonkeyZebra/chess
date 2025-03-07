package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.JoinGameRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoinGameServiceTest {
    AuthDAO auths = new MemoryAuthDAO();
    GameDAO games = new MemoryGameDAO();
    JoinGameService joinGameService = new JoinGameService(ChessGame.TeamColor.BLACK, 1, games, auths);
    JoinGameRequest request;
    AuthData authData;

    @Test
    void joinGame() throws DataAccessException {
        auths.setAuthData(new AuthData("authToken", "username"));
        authData = auths.getAuth("authToken");
        games.createGame("awesome");
        request = new JoinGameRequest(ChessGame.TeamColor.BLACK, 1);
        joinGameService.joinGame(request, authData.authToken());
        GameData joinedGame = games.getGame(1);
        assertEquals("username", joinedGame.blackUsername());
    }

    @Test
    void joinGameFail() throws DataAccessException {
        auths.setAuthData(new AuthData(null, "username"));
        authData = auths.getAuth(null);
        Assertions.assertNull(authData.authToken());
    }

    @Test
    void gameExists() throws DataAccessException {
        games.createGame("awesome");
        GameData game = games.getGameFromName("awesome");
        boolean exists = joinGameService.gameExists(game.gameID());
        assertTrue(exists);
    }

    @Test
    void gameExistsFail() throws DataAccessException {
        games.createGame("awesome");
        GameData game = games.getGameFromName("awesome");
        assertNull(games.getGame(3));
    }
}