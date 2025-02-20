package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    private Map<String, AuthData> auths;


    public void MemoryGameDAO() {
        auths = new HashMap<String, AuthData>();
    }

    public String createAuth(AuthData authData) {
        return authData.generateToken();
    }

    public AuthData getAuth(AuthData authData) {
        return auths.get(authData);
    }

    public void deleteAuth() {
        auths.clear();
    }
}
