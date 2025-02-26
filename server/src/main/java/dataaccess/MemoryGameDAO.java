package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.*;

public class MemoryGameDAO implements GameDAO {
    private Map<Integer, GameData> games;
    private int id = 1;

    public MemoryGameDAO() {
        games = new HashMap<Integer, GameData>();
    }

    public void createGame(String gameName) {
        int gameID = newGameID();
        games.put(gameID, new GameData(gameID, "user", "user", gameName, new ChessGame()));
    }

    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    public GameData getGameFromName(String gameName) {
        Collection<GameData> gameList = listGames();
        for (GameData game : gameList) {
            if (Objects.equals(game.gameName(), gameName)) {
                return game;
            }
        }
        return null;
    }

    public void updateGame(GameData game) {
        games.put(game.gameID(), game);
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
