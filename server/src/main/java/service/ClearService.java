package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class ClearService {
    /*
     * Description	Clears the database. Removes all users, games, and authTokens.
     * URL path	/db
     * HTTP Method	DELETE
     * Success response	[200] {}
     * Failure response	[500] { "message": "Error: (description of error)" }
     */
    AuthDAO authData;
    GameDAO gameData;
    UserDAO userData;
    public void clear() {
        authData.deleteAuth();
        gameData.deleteGame();
        userData.deleteUser();
    }
}
