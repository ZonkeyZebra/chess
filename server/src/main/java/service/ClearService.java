package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class ClearService {
    AuthDAO authData;
    GameDAO gameData;
    UserDAO userData;

    public ClearService(AuthDAO authData, GameDAO gameData, UserDAO userData) {
        this.authData = authData;
        this.gameData = gameData;
        this.userData = userData;
    }

    public void clear() {
        authData.deleteAuth();
        gameData.deleteGame();
        userData.deleteUser();
    }
}
