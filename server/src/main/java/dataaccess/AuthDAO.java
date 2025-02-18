package dataaccess;

import model.AuthData;

public interface AuthDAO {
    /**
     * clear: A method for clearing all data from the database. This is used during testing.
     * createAuth: Create a new authorization.
     * getAuth: Retrieve an authorization given an authToken.
     * deleteAuth: Delete an authorization so that it is no longer valid.
     */
    /// MemoryAuthDAO
    /// SQLAuthDAO
    public void createAuth(AuthData authData);
    public String getAuth(AuthData authData);
    public void deleteAuth();
}