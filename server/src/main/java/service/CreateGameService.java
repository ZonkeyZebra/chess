package service;

import dataaccess.GameDAO;
import model.CreateGameRequest;
import model.CreateGameResult;
import model.GameData;

public class CreateGameService {
    private GameDAO gameDataAccess;

    public CreateGameService(GameDAO gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    public CreateGameResult createGame(CreateGameRequest request) {
        gameDataAccess.createGame(request.gameName());
        //GameData newGame = gameDataAccess.getGame(request.gameName());
        GameData newGame = gameDataAccess.getGameFromName(request.gameName());
        CreateGameResult createGameResult = new CreateGameResult(newGame.gameID());
        return createGameResult;
    }
}
