package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

public class LoginService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;

    public LoginService(UserDAO userDataAccess, AuthDAO authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public UserData getUser(String username) throws DataAccessException {
        return userDataAccess.getUser(username);
    }

    public AuthData getAuth(AuthData authData) {
        return authDataAccess.getAuth(authData);
    }

}
