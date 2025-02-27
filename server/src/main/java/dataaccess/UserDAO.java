package dataaccess;

import model.UserData;

/// MemoryUserDAO
/// SQLUserDAO

public interface UserDAO {
    /// create a new user
    void createUser(UserData user) throws DataAccessException;
    /// retrieve a user given the username
    UserData getUser(String username) throws DataAccessException;
    /// deletes user
    void deleteUser();
}