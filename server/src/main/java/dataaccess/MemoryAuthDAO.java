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

    public AuthData getAuth(String authToken) {
        return auths.get(authToken);
    }

    public void deleteAuth() {
        auths.clear();
    }
}
