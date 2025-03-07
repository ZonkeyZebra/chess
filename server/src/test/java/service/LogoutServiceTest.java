package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MySqlAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogoutServiceTest {
    AuthDAO auths = new MySqlAuthDAO();
    LogoutService logoutService = new LogoutService(auths);

    @BeforeEach
    void setup() throws DataAccessException {
        auths.deleteAllAuths();
    }

    @Test
    void logout() throws DataAccessException {
        auths.setAuthData(new AuthData("authToken", "username"));
        AuthData authData = auths.getAuth("authToken");
        logoutService.logout(authData.authToken());
        Assertions.assertNull(auths.getAuth(authData.authToken()));
    }

    @Test
    void logoutFail() {
        AuthData authData = new AuthData(null, null);
        Assertions.assertNull(authData.authToken());
    }
}