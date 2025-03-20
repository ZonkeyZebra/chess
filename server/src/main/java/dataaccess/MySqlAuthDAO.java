package dataaccess;

import exception.DataAccessException;
import model.AuthData;

import java.sql.SQLException;

public class MySqlAuthDAO implements AuthDAO{

    public MySqlAuthDAO() {
        String[] createStatements = {
                """
            CREATE TABLE IF NOT EXISTS  auth (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            )
            """
        };
        try {
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String createAuth() {
        return AuthData.generateToken();
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        String statement = "SELECT * FROM auth WHERE authToken=?";
        String username = null;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        username = rs.getString("username");
                    }
                }
            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
        if (username == null) {
            return null;
        }
        return new AuthData(authToken, username);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        var statement = "DELETE FROM auth WHERE authToken=?";
        DatabaseManager.executeUpdate(statement, authToken);
    }

    public void setAuthData(AuthData authData) throws DataAccessException {
        String statement = "INSERT INTO auth (authToken, username) VALUES (?, ?)";
        DatabaseManager.executeUpdate(statement, authData.authToken(), authData.username());
    }

    public void deleteAllAuths() throws DataAccessException {
        String statement = "TRUNCATE auth";
        DatabaseManager.executeUpdate(statement);
    }
}
