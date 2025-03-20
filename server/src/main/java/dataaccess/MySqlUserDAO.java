package dataaccess;

import exception.DataAccessException;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Objects;

public class MySqlUserDAO implements UserDAO {

    public MySqlUserDAO() {
        String[] createStatements = {
                """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
            )
            """
        };
        try {
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUser(UserData user) throws DataAccessException {
        String statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        DatabaseManager.executeUpdate(statement, user.username(), hashedPassword, user.email());
    }

    public UserData getUser(String username, String password) throws DataAccessException {
        String statement = "SELECT * FROM user WHERE username=?";
        UserData theUser = null;
        String theUsername = null;
        String thePassword = null;
        String theEmail = null;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        theUsername = rs.getString("username");
                        thePassword = rs.getString("password");
                        theEmail = rs.getString("email");
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
        if (Objects.equals(theUsername, "")) {
            theUsername = null;
        }
        if (Objects.equals(thePassword, "")) {
            thePassword = null;
        }
        if (Objects.equals(theEmail, "")) {
            theEmail = null;
        }
        if (thePassword != null) {
            if (verifyUser(password)) {
                if (BCrypt.checkpw(password, thePassword)) {
                    theUser = new UserData(theUsername, password, theEmail);
                }
            }
        }
        return theUser;
    }

    public void deleteUser() throws DataAccessException {
        String statement = "TRUNCATE user";
        DatabaseManager.executeUpdate(statement);
    }

    private boolean verifyUser(String providedClearTextPassword) throws DataAccessException {
        // read the previously hashed password from the database
        String hashedPassword = BCrypt.hashpw(providedClearTextPassword, BCrypt.gensalt());

        return BCrypt.checkpw(providedClearTextPassword, hashedPassword);
    }
}
