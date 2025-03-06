package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MySqlUserDAOTest {
    private final UserDAO user = new MySqlUserDAO();
    private final UserData userData = new UserData("username", "password", "email@emial");

    MySqlUserDAOTest() throws SQLException, DataAccessException {
    }

    @Test
    void createUser() throws DataAccessException {
        user.createUser(userData);
    }

    @Test
    void getUser() {
    }

    @Test
    void deleteUser() throws DataAccessException {
        user.deleteUser();
    }

    @Test
    void storeUserPassword() {
    }

    @Test
    void verifyUser() {
    }
}