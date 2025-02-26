package service;

import dataaccess.GameDAO;
import model.GameData;

import java.util.Collection;

public class ListGamesService {
    private final GameDAO gameDataAccess;
    private Collection<GameData> games;

    public ListGamesService(GameDAO gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    public Collection<GameData> getGames() {
        games = gameDataAccess.listGames();
        return games;
    }
}
