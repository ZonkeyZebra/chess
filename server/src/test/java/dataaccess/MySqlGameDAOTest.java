package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MySqlGameDAOTest {
    GameDAO game = new MySqlGameDAO();
    GameData gameData1 = new GameData(1, "cool", "dude", "game1", new ChessGame());
    GameData gameData2 = new GameData(2, "lol", "o.o", "game2", new ChessGame());

    MySqlGameDAOTest() throws SQLException, DataAccessException { }

    @BeforeEach
    void setup() throws DataAccessException {
        game.deleteGame();
        game.createGame("game1");
    }

    @Test
    void createGame() throws DataAccessException {
        game.createGame("game2");
        Assertions.assertNotNull(game.getGame(2));
    }

    @Test
    void createGameFail() {
        Assertions.assertThrows(DataAccessException.class, () -> game.createGame(null));
    }

    @Test
    void getGame() throws DataAccessException {
        var result = game.getGame(1);
        System.out.println(result);
        Assertions.assertNotNull(result);
    }

    @Test
    void getGameFail() throws DataAccessException {
        Assertions.assertNull(game.getGame(0).gameName());
    }

    @Test
    void getGameFromName() throws DataAccessException {
        var result = game.getGameFromName("game1");
        System.out.println(result);
        Assertions.assertNotNull(result);
    }

    @Test
    void getGameFromNameFail() throws DataAccessException {
        Assertions.assertNull(game.getGame(0).gameName());
    }

    @Test
    void listGames() throws DataAccessException {
        var list = game.listGames();
        System.out.println(list);
        Assertions.assertNotNull(list);
    }

    @Test
    void listGamesFail() throws DataAccessException {
        game.deleteGame();
        Collection<GameData> expected = new ArrayList<>();
        Assertions.assertEquals(expected, game.listGames());
    }

    @Test
    void updateGame() throws DataAccessException {
        game.updateGame(gameData1);
        System.out.println(game.getGame(gameData1.gameID()));
        Assertions.assertNotNull(game.getGame(gameData1.gameID()));
    }

    @Test
    void updateGamesFail() throws DataAccessException {
        Assertions.assertEquals("", game.getGame(gameData1.gameID()).blackUsername());
    }

    @Test
    void deleteGame() throws DataAccessException {
        game.deleteGame();
        Collection<GameData> expected = new ArrayList<>();
        Assertions.assertEquals(expected, game.listGames());
    }

    @Test
    void deleteGamesFail() throws DataAccessException {
        Assertions.assertNotNull(game.listGames());
    }
}