package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MySqlUserDAOTest {
    private final UserDAO user = new MySqlUserDAO();
    private final UserData userData1 = new UserData("username", "password", "email@emial");
    private final UserData userData2 = new UserData("awesome", "dude", "awesome@dude");

    MySqlUserDAOTest() throws SQLException, DataAccessException {
    }

    @Test
    void createUser() throws DataAccessException {
        user.createUser(userData1);
        user.createUser(userData2);
    }

    @Test
    void getUser() throws DataAccessException {
        var result = user.getUser(userData2.username(), userData2.password());
        System.out.println(result);
        Assertions.assertEquals(userData2, result);
    }

    @Test
    void deleteUser() throws DataAccessException {
        user.deleteUser();
    }


    @Test
    void verifyUser() {
    }
}