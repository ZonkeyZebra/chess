package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

public class MySqlUserDAO implements UserDAO {

    public void createUser(UserData user) throws DataAccessException {
        //TODO
    }

    public UserData getUser(String username) throws DataAccessException {
        //TODO
        return null;
    }

    public void deleteUser() {
        //TODO
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`username`),
            )
            """
    };

    private void configureDatabase() throws DataAccessException {
        //TODO
    }

    void storeUserPassword(String username, String clearTextPassword) {
        String hashedPassword = BCrypt.hashpw(clearTextPassword, BCrypt.gensalt());

        // write the hashed password in database along with the user's other information
        writeHashedPasswordToDatabase(username, hashedPassword);
    }

    private void writeHashedPasswordToDatabase(String username, String hashedPassword) {
        // TODO
    }

    boolean verifyUser(String username, String providedClearTextPassword) {
        // read the previously hashed password from the database
        var hashedPassword = readHashedPasswordFromDatabase(username);

        return BCrypt.checkpw(providedClearTextPassword, hashedPassword);
    }

    private String readHashedPasswordFromDatabase(String username) {
        //TODO
        return "hashedPassword";
    }
}
