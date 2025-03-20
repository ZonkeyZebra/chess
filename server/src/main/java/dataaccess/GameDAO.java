package dataaccess;

import exception.DataAccessException;
import model.GameData;

import java.util.Collection;
import java.util.Objects;

public interface GameDAO {
    /// creates a new game
    public void createGame(String gameName) throws DataAccessException;
    /// retrieve a specified game with the given gameID
    public GameData getGame(int gameID) throws DataAccessException;
    public GameData getGameFromName(String gameName) throws DataAccessException;
    /// retrieve all games
    public Collection<GameData> listGames() throws DataAccessException;
    /// Updates a chess game. It should replace the chess game string corresponding to a given gameID.
    public void updateGame(GameData game) throws DataAccessException;
    /// deletes game
    public void deleteGame() throws DataAccessException;
}

class GameDataAccessShared {
    public GameData gameFromName(String gameName, Collection<GameData> gameList) {
        for (GameData game : gameList) {
            if (Objects.equals(game.gameName(), gameName)) {
                return game;
            }
        }
        return null;
    }
}