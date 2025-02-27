package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.LoginRequest;
import model.LoginResult;
import model.UserData;

import java.util.Objects;

public class LoginService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;
    private String username;
    private String password;
    private LoginResult result;

    public LoginService(UserDAO userDataAccess, AuthDAO authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        username = request.username();
        password = request.password();
        if (getUser(request.username()) == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        if (!Objects.equals(getUser(request.username()).password(), request.password())) {
            throw new DataAccessException("Error: unauthorized");
        }
        String authToken = authDataAccess.createAuth();
        authDataAccess.setAuthData(new AuthData(authToken, username));
        result = new LoginResult(username, authToken);
        return result;
    }

    public UserData getUser(String username) throws DataAccessException {
        return userDataAccess.getUser(username);
    }

    public AuthData getAuth(String authToken) {
        return authDataAccess.getAuth(authToken);
    }

}
