package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.DataAccessException;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class MySqlGameDAO implements GameDAO {
    GameDataAccessShared shared = new GameDataAccessShared();
    private int id = 1;
    int gameID = 0;
    String whiteUsername = null;
    String blackUsername = null;
    String gameName = null;
    ChessGame game = null;
    String json = null;
    ArrayList<GameData> result = new ArrayList<GameData>();

    public MySqlGameDAO() {
        String[] createStatements = {
                """
            CREATE TABLE IF NOT EXISTS  game (
              `id` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256) NOT NULL,
              `blackUsername` varchar(256) NOT NULL,
              `gameName` varchar(256) NOT NULL,
              `game` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`)
            )
            """
        };
        try {
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createGame(String gameName) throws DataAccessException {
        int gameID = newGameID();
        GameData game = new GameData(gameID, "", "", gameName, new ChessGame());
        String statement = "INSERT INTO game (id, whiteUsername, blackUsername, gameName, game, json) VALUES (?, ?, ?, ?, ?, ?)";
        var json = new Gson().toJson(game.game());
        DatabaseManager.executeUpdate(statement, game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), game.game(), json);
    }

    public GameData getGame(int gameID) throws DataAccessException {
        resetValues();
        String statement = "SELECT * FROM game WHERE id=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    readGetGame(rs);
                }
            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
        game = new Gson().fromJson(json, ChessGame.class);
        return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
    }

    public GameData getGameFromName(String gameName) throws DataAccessException {
        Collection<GameData> gameList = listGames();
        return shared.gameFromName(gameName, gameList);
    }

    public Collection<GameData> listGames() throws DataAccessException {
        resetValues();
        result = new ArrayList<GameData>();
        String statement = "SELECT * FROM game";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    readGameList(rs);
                }
            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
        return result;
    }

    public void updateGame(GameData game) throws DataAccessException {
        String statement = "UPDATE game SET whiteUsername = ?, blackUsername = ?, gameName = ?, game = ?, json = ? WHERE id = ?;";
        var json = new Gson().toJson(game.game());
        DatabaseManager.executeUpdate(statement, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game(), json, game.gameID());
    }

    public void deleteGame() throws DataAccessException {
        String statement = "TRUNCATE game";
        DatabaseManager.executeUpdate(statement);
    }

    private int newGameID() {
        return id++;
    }

    private void readGetGame(ResultSet rs) throws SQLException {
        while (rs.next()) {
            whiteUsername = rs.getString("whiteUsername");
            blackUsername = rs.getString("blackUsername");
            gameName = rs.getString("gameName");
            json = rs.getString("json");
        }
    }

    private void readGameList(ResultSet rs) throws SQLException {
        while (rs.next()) {
            gameID = rs.getInt("id");
            whiteUsername = rs.getString("whiteUsername");
            blackUsername = rs.getString("blackUsername");
            gameName = rs.getString("gameName");
            json = rs.getString("json");
            game = new Gson().fromJson(json, ChessGame.class);
            if (Objects.equals(blackUsername, "")) {
                blackUsername = null;
            }
            if (Objects.equals(whiteUsername, "")) {
                whiteUsername = null;
            }
            result.add(new GameData(gameID, whiteUsername, blackUsername, gameName, game));
        }
    }

    private void resetValues() {
        gameID = 0;
        whiteUsername = null;
        blackUsername = null;
        gameName = null;
        game = null;
        json = null;
    }
}
