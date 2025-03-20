package service;

import dataaccess.*;
import exception.DataAccessException;

public class ClearService {
    private AuthDAO auths = new MySqlAuthDAO();
    private GameDAO games = new MySqlGameDAO();
    private UserDAO users = new MySqlUserDAO();

    public ClearService(AuthDAO auths, GameDAO games, UserDAO users) {
        this.auths = auths;
        this.games = games;
        this.users = users;
    }

    public void clear() throws DataAccessException {
        auths.deleteAllAuths();
        games.deleteGame();
        users.deleteUser();
    }
}
