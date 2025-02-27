package service;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;

public class LogoutService {
    private final AuthDAO auths;

    public LogoutService(AuthDAO auths) {
        this.auths = auths;
    }

    public void logout(String authToken) throws DataAccessException {
        if (authToken == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        auths.deleteAuth();
    }

}
