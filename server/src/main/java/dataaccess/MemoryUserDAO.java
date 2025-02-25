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
        users.put(user.username(), user);
    }

    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }

    public void deleteUser() {
        users.clear();
    }
}
