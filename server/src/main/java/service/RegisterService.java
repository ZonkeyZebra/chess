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
    private String username;
    private String password;
    private String email;
    private String authToken;

    public RegisterService(UserDAO userDataAccess, AuthDAO authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        username = request.username();
        password = request.password();
        email = request.email();
        UserData user = new UserData(username, password, email);
        createUser(user);
        authToken = createAuth();
        RegisterResult result = new RegisterResult(username, authToken);
        return result;
    }

    public UserData getUser(String username) throws DataAccessException {
        return userDataAccess.getUser(username);
    }

    public void createUser(UserData user) throws DataAccessException {
        userDataAccess.createUser(user);
    }

    public String createAuth() {
        return authDataAccess.createAuth();
    }
}
