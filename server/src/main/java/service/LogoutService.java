package service;
import dataaccess.AuthDAO;
import exception.DataAccessException;
import dataaccess.MySqlAuthDAO;
import model.AuthData;

public class LogoutService {
    private AuthDAO auths = new MySqlAuthDAO();

    public LogoutService(AuthDAO auths) {
        this.auths = auths;
    }

    public void logout(String authToken) throws DataAccessException {
        AuthData authData = auths.getAuth(authToken);
        if (authData == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        auths.deleteAuth(authToken);
    }

}
