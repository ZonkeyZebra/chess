package dataaccess;

import model.GameData;

import java.util.Collection;

/// MemoryGameDAO
/// SQLGameDAO

public interface GameDAO {
    /// creates a new game
    public void createGame(String gameName);
    /// retrieve a specified game with the given gameID
    public GameData getGame(int gameID);
    public GameData getGameFromName(String gameName);
    /// retrieve all games
    public Collection<GameData> listGames();
    /// Updates a chess game. It should replace the chess game string corresponding to a given gameID. This is used when players join a game or when a move is made.
    public void updateGame(GameData game);
    /// deletes game
    public void deleteGame();
}
