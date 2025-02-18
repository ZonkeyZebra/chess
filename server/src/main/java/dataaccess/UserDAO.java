package dataaccess;

import model.UserData;

public interface UserDAO {
    /**
     * clear: A method for clearing all data from the database. This is used during testing.
     * createUser: Create a new user.
     * getUser: Retrieve a user with the given username.
     * deleteUser: Deletes users
     */
    /// MemoryUserDAO
    /// SQLUserDAO
    public void createUser(UserData userData);
    public void getUser(UserData username); /// not sure on type
    public void deleteUser();
}
