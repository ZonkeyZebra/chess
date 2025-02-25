package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {
    private Map<String, UserData> users;

    public MemoryUserDAO() {
        users = new HashMap<String, UserData>();
    }

    public void createUser(UserData user) throws DataAccessException {
        if (user.username() == null || user.password() == null || user.email() == null) {
            throw new DataAccessException("bad request");
        }
        if (user == getUser(user.username())) {
            throw new DataAccessException("already taken");
        }
        users.put(user.username(), user);
    }

    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }

    public void deleteUser() {
        users.clear();
    }
}
