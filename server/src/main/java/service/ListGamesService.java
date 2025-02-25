package service;

import dataaccess.GameDAO;
import model.GameData;
import model.ListGamesResult;

import java.util.Collection;

public class ListGamesService {
    private final GameDAO gameDataAccess;
    private Collection<GameData> games;
    private Collection<ListGamesResult> gameList;

    public ListGamesService(GameDAO gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    public Collection<ListGamesResult> listGames() {
        games = gameDataAccess.listGames();
        for (GameData game : games) {
            gameList.add(new ListGamesResult(game.gameID(), game.blackUsername(), game.whiteUsername(), game.gameName()));
        }
        return gameList;
    }
}
