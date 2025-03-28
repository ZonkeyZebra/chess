package dataaccess;

import exception.DataAccessException;
import model.UserData;

public interface UserDAO {
    /// create a new user
    void createUser(UserData user) throws DataAccessException;
    /// retrieve a user given the username
    UserData getUser(String username, String password) throws DataAccessException;
    /// deletes user
    void deleteUser() throws DataAccessException;
}