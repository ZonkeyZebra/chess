package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.LoginRequest;
import model.LoginResult;
import model.UserData;

public class LoginService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;
    private String username;
    private String password;

    public LoginService(UserDAO userDataAccess, AuthDAO authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public LoginResult login(LoginRequest request) {
        username = request.username();
        password = request.password();
        LoginResult result = new LoginResult(username, password);
        return result;
    }

    public UserData getUser(String username) throws DataAccessException {
        return userDataAccess.getUser(username);
    }

    public AuthData getAuth(AuthData authData) {
        return authDataAccess.getAuth(authData);
    }

}
