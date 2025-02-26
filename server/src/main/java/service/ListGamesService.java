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

    public ListGamesService(GameDAO gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    public Collection<GameData> getGames() {
        games = gameDataAccess.listGames();
        return games;
    }

//    public Collection<ListGamesResult> listGames() {
//        gameList.add(new ListGamesResult(0, null, null, null));
//        games = gameDataAccess.listGames();
//        if (games != null) {
//            gameList.clear();
//        }
//        for (GameData game : games) {
//            gameList.add(new ListGamesResult(game.gameID(), game.blackUsername(), game.whiteUsername(), game.gameName()));
//        }
//        return gameList;
//    }
}
