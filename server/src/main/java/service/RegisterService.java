package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.RegisterRequest;
import model.RegisterResult;
import model.UserData;

public class RegisterService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;

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
        if (user.equals(getUser(username))) {
            throw new DataAccessException("Error: already taken");
        }
        createUser(user);
        String authToken = authDataAccess.createAuth();
        authDataAccess.setAuthData(new AuthData(authToken, user.username()));
        return new RegisterResult(username, authToken);
    }

    public UserData getUser(String username) throws DataAccessException {
        return userDataAccess.getUser(username);
    }

    public void createUser(UserData user) throws DataAccessException {
        userDataAccess.createUser(user);
    }
}
