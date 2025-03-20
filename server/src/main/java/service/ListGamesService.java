package service;

import dataaccess.*;
import exception.DataAccessException;
import model.AuthData;
import model.GameData;

import java.util.Collection;

public class ListGamesService {
    private GameDAO gameDataAccess = new MySqlGameDAO();
    private AuthDAO authDataAccess = new MySqlAuthDAO();

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
