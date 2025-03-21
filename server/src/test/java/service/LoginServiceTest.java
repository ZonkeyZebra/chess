package service;

import dataaccess.*;
import exception.DataAccessException;
import model.AuthData;
import model.LoginRequest;
import model.LoginResult;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginServiceTest {
    AuthDAO auths = new MySqlAuthDAO();
    UserDAO users = new MySqlUserDAO();
    LoginService loginService = new LoginService(users, auths);
    LoginRequest request;
    LoginResult result;
    AuthData authData;
    UserData userData;

    @BeforeEach
    void setUp() throws DataAccessException {
        auths.deleteAllAuths();
        users.deleteUser();
        authData = new AuthData("token", "name");
        auths.setAuthData(authData);
        userData = new UserData("name", "pass", "mial@mail");
        users.createUser(userData);
        request = new LoginRequest("name", "pass");
    }

    @Test
    void login() throws DataAccessException {
        result = loginService.login(request);
        System.out.println(result);
        Assertions.assertNotNull(result);
    }

    @Test
    void loginFail() throws DataAccessException {
        LoginRequest badRequest = new LoginRequest("name", "pss");
        Assertions.assertThrows(RuntimeException.class, () -> loginService.getUser(badRequest.username(), badRequest.password()).password());
    }

    @Test
    void getUser() throws DataAccessException {
        Assertions.assertEquals(loginService.getUser(request.username(), request.password()).username(), request.username());
    }

    @Test
    void getUserFail() throws DataAccessException {
        Assertions.assertNotEquals(loginService.getUser(request.username(), request.password()).password(), request.username());
    }

    @Test
    void getAuth() throws DataAccessException {
        Assertions.assertEquals(loginService.getAuth("token"), authData);
    }

    @Test
    void getAuthFail() throws DataAccessException {
        Assertions.assertNotEquals(loginService.getAuth("tken"), authData);
    }
}