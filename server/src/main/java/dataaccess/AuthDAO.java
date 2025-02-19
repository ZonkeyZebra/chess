package dataaccess;

import model.AuthData;

/// MemoryAuthDAO
/// SQLAuthDAO

public interface AuthDAO {
    /// create new authorization
    public void createAuth(AuthData authData);
    /// retrieve an authorization given an authToken
    public AuthData getAuth(AuthData authData);
    /// delete authorization so it's no longer valid
    public void deleteAuth();
}