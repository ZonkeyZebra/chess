package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MySqlAuthDAOTest {
    AuthDAO auth = new MySqlAuthDAO();
    AuthData authData1 = new AuthData(AuthData.generateToken(), "username");
    AuthData authData2 = new AuthData(AuthData.generateToken(), "awesome");

    MySqlAuthDAOTest() throws SQLException, DataAccessException { }

    @BeforeEach
    void setup() throws DataAccessException {
        auth.deleteAllAuths();
        auth.setAuthData(authData1);
    }

    @Test
    void createAuth() {
    }

    @Test
    void getAuth() throws DataAccessException {
        var result = auth.getAuth(authData1.authToken());
        Assertions.assertEquals(authData1, result);
    }

    @Test
    void deleteAuth() throws DataAccessException {
        auth.deleteAuth(authData1.authToken());
        Assertions.assertNotNull(auth.getAuth(authData2.authToken()));
    }

    @Test
    void setAuthData() throws DataAccessException {
        auth.setAuthData(authData2);
        Assertions.assertNotNull(auth.getAuth(authData2.authToken()));
    }

    @Test
    void deleteAllAuths() throws DataAccessException {
        auth.deleteAllAuths();
    }
}