package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.CreateGameRequest;
import model.CreateGameResult;
import model.GameData;

public class CreateGameService {
    private GameDAO gameDataAccess;
    private AuthDAO authDataAccess;

    public CreateGameService(GameDAO gameDataAccess, AuthDAO authDataAccess) {
        this.gameDataAccess = gameDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public CreateGameResult createGame(CreateGameRequest request, String authToken) throws DataAccessException {
        AuthData authData = authDataAccess.getAuth(authToken);
        if (authData == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        gameDataAccess.createGame(request.gameName());
        GameData newGame = gameDataAccess.getGameFromName(request.gameName());
        CreateGameResult createGameResult = new CreateGameResult(newGame.gameID());
        return createGameResult;
    }
}
