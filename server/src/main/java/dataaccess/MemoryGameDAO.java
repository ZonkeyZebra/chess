package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {
    private Map<String, GameData> games;
    private int id = 0;

    public MemoryGameDAO() {
        games = new HashMap<String, GameData>();
    }

    public void createGame(String gameName) {
        games.put(gameName, new GameData(newGameID(), "user", "user", gameName, new ChessGame()));
    }

    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    public GameData getGameID(String gameName) {
        return games.get(gameName);
    }

    public void updateGame(GameData gameID) {

    }

    public Collection<GameData> listGames() {
        return games.values();
    }

    public void deleteGame() {
        games.clear();
    }

    public int newGameID() {
        return id++;
    }

}
