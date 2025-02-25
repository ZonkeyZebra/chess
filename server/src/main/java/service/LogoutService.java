package service;
import dataaccess.AuthDAO;

public class LogoutService {
    private final AuthDAO auths;

    public LogoutService(AuthDAO auths) {
        this.auths = auths;
    }

    public void logout() {
        auths.deleteAuth();
    }

}
