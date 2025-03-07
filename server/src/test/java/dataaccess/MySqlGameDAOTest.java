package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
    }

    @Test
    void getGame() throws DataAccessException {
        var result = game.getGame(1);
        System.out.println(result);
    }

    @Test
    void getGameFromName() throws DataAccessException {
        var result = game.getGameFromName("game1");
        System.out.println(result);
    }

    @Test
    void listGames() throws DataAccessException {
        var list = game.listGames();
        System.out.println(list);
    }

    @Test
    void updateGame() throws DataAccessException {
        game.updateGame(gameData1);
    }

    @Test
    void deleteGame() throws DataAccessException {
        game.deleteGame();
    }
}