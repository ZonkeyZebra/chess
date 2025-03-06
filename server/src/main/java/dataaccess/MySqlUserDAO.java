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

    public UserData getUser(String username) throws DataAccessException {
        String statement = "SELECT username, json FROM user WHERE username=?";
        //TODO
        return null;
    }

    public void deleteUser() throws DataAccessException {
        String statement = "TRUNCATE user";
        DatabaseManager.executeUpdate(statement);
    }

    boolean verifyUser(String username, String providedClearTextPassword) throws DataAccessException {
        // read the previously hashed password from the database
        String hashedPassword = readHashedPasswordFromDatabase(username);

        return BCrypt.checkpw(providedClearTextPassword, hashedPassword);
    }

    private String readHashedPasswordFromDatabase(String username) throws DataAccessException {
        String password = getUser(username).password();
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
