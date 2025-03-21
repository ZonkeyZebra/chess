package service;

import dataaccess.*;
import exception.DataAccessException;
import model.AuthData;
import model.LoginRequest;
import model.LoginResult;
import model.UserData;

import java.util.Objects;

public class LoginService {
    private UserDAO userDataAccess = new MySqlUserDAO();
    private AuthDAO authDataAccess = new MySqlAuthDAO();

    public LoginService(UserDAO userDataAccess, AuthDAO authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        String username = request.username();
        UserData theUser = getUser(request.username(), request.password());
        if (theUser == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        if (!Objects.equals(theUser.password(), request.password())) {
            throw new DataAccessException("Error: unauthorized");
        }
        String authToken = authDataAccess.createAuth();
        authDataAccess.setAuthData(new AuthData(authToken, username));
        return new LoginResult(username, authToken);
    }

    public UserData getUser(String username, String password) throws DataAccessException {
        return userDataAccess.getUser(username, password);
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return authDataAccess.getAuth(authToken);
    }

}
