package dataaccess;

import model.UserData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MySqlUserDAOTest {
    private final UserDAO user = new MySqlUserDAO();
    private final UserData userData1 = new UserData("username", "password", "email@emial");
    private final UserData userData2 = new UserData("awesome", "dude", "awesome@dude");

    MySqlUserDAOTest() throws SQLException, DataAccessException { }

    @BeforeEach
    void setup() throws DataAccessException {
        user.deleteUser();
        user.createUser(userData1);
    }

    @Test
    void createUser() throws DataAccessException {
        user.createUser(userData2);
        Assertions.assertNotNull(user.getUser(userData2.username(), userData2.password()));
    }

    @Test
    void getUser() throws DataAccessException {
        var result = user.getUser(userData1.username(), userData1.password());
        System.out.println(result);
        Assertions.assertEquals(userData1, result);
    }


    @Test
    void verifyUser() {
    }

    @Test
    void deleteUser() throws DataAccessException {
        user.deleteUser();
    }
}