package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;

import java.util.Collection;

public class ListGamesService {
    private final GameDAO gameDataAccess;
    private final AuthDAO authDataAccess;

    public ListGamesService(GameDAO gameDataAccess, AuthDAO authDataAccess) {
        this.gameDataAccess = gameDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public Collection<GameData> getGames(String authToken) throws DataAccessException {
        AuthData authData = authDataAccess.getAuth(authToken);
        if (authData == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        return gameDataAccess.listGames();
    }
}
