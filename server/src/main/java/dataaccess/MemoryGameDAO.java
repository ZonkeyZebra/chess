package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {
    private Map<String, GameData> games;


    public MemoryGameDAO() {
        games = new HashMap<String, GameData>();
    }

    public void createGame(GameData gameID) {

    }

    public GameData getGame(GameData gameID) {
        return games.get(gameID);
    }

    public void updateGame(GameData gameID) {

    }

    public Collection<GameData> listGames() {
        return games.values();
    }

    public void deleteGame() {
        games.clear();
    }

}
