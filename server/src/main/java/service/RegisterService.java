package service;

import dataaccess.*;
import exception.DataAccessException;
import model.AuthData;
import model.RegisterRequest;
import model.RegisterResult;
import model.UserData;

public class RegisterService {
    private UserDAO userDataAccess = new MySqlUserDAO();
    private AuthDAO authDataAccess = new MySqlAuthDAO();

    public RegisterService(UserDAO userDataAccess, AuthDAO authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        String username = request.username();
        String password = request.password();
        String email = request.email();
        if (username == null || password == null || email == null) {
            throw new DataAccessException("Error: bad request");
        }
        UserData user = new UserData(username, password, email);
        if (user.equals(getUser(username, password))) {
            throw new DataAccessException("Error: already taken");
        }
        createUser(user);
        String authToken = authDataAccess.createAuth();
        authDataAccess.setAuthData(new AuthData(authToken, user.username()));
        return new RegisterResult(username, authToken);
    }

    public UserData getUser(String username, String password) throws DataAccessException {
        return userDataAccess.getUser(username, password);
    }

    public void createUser(UserData user) throws DataAccessException {
        userDataAccess.createUser(user);
    }
}
