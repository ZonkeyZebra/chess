package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class ClearService {
    private final AuthDAO auths;
    private final GameDAO games;
    private final UserDAO users;

    public ClearService(AuthDAO auths, GameDAO games, UserDAO users) {
        this.auths = auths;
        this.games = games;
        this.users = users;
    }

    public void clear() {
        auths.deleteAllAuths();
        games.deleteGame();
        users.deleteUser();
    }
}
