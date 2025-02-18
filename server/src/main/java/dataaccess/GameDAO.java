package dataaccess;

import chess.ChessGame;
import model.GameData;

public interface GameDAO {
    /**
     * clear: A method for clearing all data from the database. This is used during testing.
     * createGame: Create a new game.
     * getGame: Retrieve a specified game with the given game ID.
     * listGames: Retrieve all games.
     * updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID. This is used when players join a game or when a move is made.
     * deleteGame: Delete games
     */
    /// MemoryGameDAO
    /// SQLGameDAO
    public ChessGame createGame(GameData gameID);
    public ChessGame getGame(GameData gameID);
    public ChessGame updateGame(GameData gameID);
    public void deleteGame();
}
