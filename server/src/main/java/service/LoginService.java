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

    public LoginService(UserDAO userDataAccess, AuthDAO authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        String username = request.username();
        if (getUser(request.username(), request.password()) == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        if (!Objects.equals(getUser(request.username(), request.password()).password(), request.password())) {
            throw new DataAccessException("Error: unauthorized");
        }
        String authToken = authDataAccess.createAuth();
        authDataAccess.setAuthData(new AuthData(authToken, username));
        return new LoginResult(username, authToken);
    }

    public UserData getUser(String username, String password) throws DataAccessException {
        return userDataAccess.getUser(username, password);
    }

    public AuthData getAuth(String authToken) {
        return authDataAccess.getAuth(authToken);
    }

}
