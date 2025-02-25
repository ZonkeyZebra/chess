package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {
    private Map<String, GameData> games;
    private int id = 1;

    public MemoryGameDAO() {
        games = new HashMap<String, GameData>();
    }

    public void createGame(String gameName) {
        games.put(gameName, new GameData(newGameID(), "user", "user", gameName, new ChessGame()));
    }

    public GameData getGame(String gameName) {
        return games.get(gameName);
    }

    public GameData getGameFromID(int gameID) {
        Collection<GameData> gameList = listGames();
        for (GameData game : gameList) {
            if (game.gameID() == gameID) {
                return game;
            }
        }
        return null;
    }

    public void updateGame(GameData game) {
        games.put(game.gameName(), game);
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
