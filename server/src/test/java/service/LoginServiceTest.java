package service;

import dataaccess.*;
import model.AuthData;
import model.LoginRequest;
import model.LoginResult;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    AuthDAO auths = new MemoryAuthDAO();
    UserDAO users = new MemoryUserDAO();
    LoginService loginService = new LoginService(users, auths);
    LoginRequest request;
    LoginResult result;
    AuthData authData;
    UserData userData;

    @BeforeEach
    void setUp() throws DataAccessException {
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
        Assertions.assertNotEquals(loginService.getUser(badRequest.username()).password(), badRequest.password());
    }

    @Test
    void getUser() throws DataAccessException {
        Assertions.assertEquals(loginService.getUser(request.username()).username(), request.username());
    }

    @Test
    void getUserFail() throws DataAccessException {
        Assertions.assertNotEquals(loginService.getUser(request.username()).password(), request.username());
    }

    @Test
    void getAuth() {
        Assertions.assertEquals(loginService.getAuth("token"), authData);
    }

    @Test
    void getAuthFail() {
        Assertions.assertNotEquals(loginService.getAuth("tken"), authData);
    }
}