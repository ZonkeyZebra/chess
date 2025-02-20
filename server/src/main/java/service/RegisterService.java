package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

public class RegisterService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;

    public RegisterService(UserDAO userDataAccess, AuthDAO authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public UserData getUser(String username) throws DataAccessException {
        return userDataAccess.getUser(username);
    }

    public void createUser(UserData user) throws DataAccessException {
        if (getUser(user.username()) == null) {
            userDataAccess.createUser(user);
        }
    }

    public String createAuth(AuthData authData) {
        return authDataAccess.createAuth(authData);
    }
}
