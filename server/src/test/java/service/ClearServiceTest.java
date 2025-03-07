package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
    AuthDAO auths = new MemoryAuthDAO();
    GameDAO games = new MemoryGameDAO();
    UserDAO users = new MemoryUserDAO();
    ClearService clearService = new ClearService(auths, games, users);

    @BeforeEach
    void setUp() throws DataAccessException {
        auths.setAuthData(new AuthData("token", "angela"));
        games.createGame("Heaven");
        users.createUser(new UserData("angela", "angel", "heaven@above,com"));
    }

    @Test
    void clear() throws DataAccessException {
        clearService.clear();
        Assertions.assertNull(auths.getAuth("token"));
        Assertions.assertNull(games.getGame(1));
        Assertions.assertNull(users.getUser("angela", "angel"));
    }

    @Test
    void clearFail() throws DataAccessException {
        Assertions.assertNotNull(auths.getAuth("token"));
        Assertions.assertNotNull(games.getGame(1));
        Assertions.assertNotNull(users.getUser("angela", "angel"));
    }
}