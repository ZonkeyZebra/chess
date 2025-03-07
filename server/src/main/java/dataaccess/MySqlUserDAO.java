package dataaccess;

import chess.ChessGame;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

import static java.sql.Types.NULL;

public class MySqlUserDAO implements UserDAO {

    public MySqlUserDAO() throws SQLException, DataAccessException {
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
        DatabaseManager.configureDatabase(createStatements);
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
        if (verifyUser(username, password)) {
            theUser = new UserData(theUsername, password, theEmail);
        }
        return theUser;
    }

    public void deleteUser() throws DataAccessException {
        String statement = "TRUNCATE user";
        DatabaseManager.executeUpdate(statement);
    }

    boolean verifyUser(String username, String providedClearTextPassword) throws DataAccessException {
        // read the previously hashed password from the database
        String hashedPassword = readHashedPasswordFromDatabase(username, providedClearTextPassword);

        return BCrypt.checkpw(providedClearTextPassword, hashedPassword);
    }

    private String readHashedPasswordFromDatabase(String username, String password) throws DataAccessException {
        String thePassword = getUser(username, password).password();
        return BCrypt.hashpw(thePassword, BCrypt.gensalt());
    }
}
