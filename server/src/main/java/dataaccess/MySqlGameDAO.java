package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.List;

public class MySqlGameDAO implements GameDAO {
    @Override
    public void createGame(String gameName) {
        //TODO
    }

    @Override
    public GameData getGame(int gameID) {
        //TODO
        return null;
    }

    @Override
    public GameData getGameFromName(String gameName) {
        //TODO
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        //TODO
        return List.of();
    }

    @Override
    public void updateGame(GameData game) {
        //TODO
    }

    @Override
    public void deleteGame() {
        //TODO
    }
}
