package dataaccess;

import exception.DataAccessException;
import model.AuthData;


public interface AuthDAO {
    /// create new authorization
    public String createAuth();
    /// retrieve an authorization given an authToken
    public AuthData getAuth(String authToken) throws DataAccessException;
    /// delete authorization so it's no longer valid
    public void deleteAuth(String authToken) throws DataAccessException;
    public void setAuthData(AuthData authData) throws DataAccessException;
    public void deleteAllAuths() throws DataAccessException;
}