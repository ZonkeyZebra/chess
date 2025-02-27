package service;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;

public class LogoutService {
    private final AuthDAO auths;

    public LogoutService(AuthDAO auths) {
        this.auths = auths;
    }

    public void logout(String authToken) throws DataAccessException {
        AuthData authData = auths.getAuth(authToken);
        if (authData == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        auths.deleteAuth();
    }

}
