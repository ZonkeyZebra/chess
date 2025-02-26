package service;

import dataaccess.GameDAO;
import model.GameData;
import model.ListGamesResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class ListGamesService {
    private final GameDAO gameDataAccess;
    private Collection<GameData> games;
    private Collection<ListGamesResult> gameList = new ArrayList<>();
    private ListGamesResult result;

    public ListGamesService(GameDAO gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    public Collection<GameData> getGames() {
        games = gameDataAccess.listGames();
        return games;
    }
}
