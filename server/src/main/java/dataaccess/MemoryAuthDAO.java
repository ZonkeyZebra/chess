package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    private Map<String, AuthData> auths;


    public MemoryAuthDAO() {
        auths = new HashMap<String, AuthData>();
    }

    public String createAuth() {
        return AuthData.generateToken();
    }

    public AuthData getAuth(AuthData authData) {
        return auths.get(authData.authToken());
    }

    public void deleteAuth() {
        auths.clear();
    }
}
