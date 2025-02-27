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

    public void setAuthData(AuthData authData) {
        auths.put(authData.authToken(), authData);
    }

    public AuthData getAuth(String authToken) {
        return auths.get(authToken);
    }

    public void deleteAuth(String authToken) {
        auths.remove(authToken);
    }

    public void deleteAllAuths() {
        auths.clear();
    }
}
