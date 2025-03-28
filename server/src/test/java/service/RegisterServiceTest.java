package service;

import dataaccess.*;
import exception.DataAccessException;
import model.RegisterRequest;
import model.RegisterResult;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegisterServiceTest {
    UserDAO user = new MySqlUserDAO();
    AuthDAO auth = new MySqlAuthDAO();
    RegisterService registerService;
    RegisterRequest request;
    RegisterResult result;

    @BeforeEach
    void setUp() throws DataAccessException {
        user.deleteUser();
        auth.deleteAllAuths();
        registerService = new RegisterService(user, auth);
    }

    @Test
    void register() throws DataAccessException {
        request = new RegisterRequest("angela", "angel", "heaven@above.com");
        result = registerService.register(request);
        System.out.println(result);
        Assertions.assertNotNull(result);
    }

    @Test
    void registerFail() {
        request = new RegisterRequest(null, "angel", "heaven@above.com");
        Assertions.assertNull(request.username());
    }

    @Test
    void getUser() throws DataAccessException {
        UserData userData = new UserData("angela", "angel", "heaven@above.com");
        registerService.createUser(userData);
        UserData actual = registerService.getUser("angela", "angel");
        Assertions.assertEquals(userData, actual);
    }

    @Test
    void getUserFail() throws DataAccessException {
        UserData userData = new UserData("angela", "angel", "heaven@above.com");
        registerService.createUser(userData);
        UserData actual = registerService.getUser("angel", "angel");
        Assertions.assertNotEquals(userData, actual);
    }

    @Test
    void createUser() throws DataAccessException {
        UserData userData = new UserData("angela", "angel", "heaven@above.com");
        registerService.createUser(userData);
        Assertions.assertNotNull(registerService.getUser(userData.username(), userData.password()));
    }

    @Test
    void createUserFail() throws DataAccessException {
        UserData userData = new UserData("angela", "angel", null);
        Assertions.assertNull(registerService.getUser(userData.username(), userData.password()));
    }
}