package server;

import dataaccess.*;
import model.RegisterRequest;
import model.RegisterResult;
import service.RegisterService;

public class testing {
    public static void main(String[] args) throws DataAccessException {
        UserDAO user = new MemoryUserDAO();
        AuthDAO auth = new MemoryAuthDAO();
        RegisterService registerService = new RegisterService(user, auth);
        RegisterRequest request = new RegisterRequest("alice", "angel", "heaven@above.com");
        RegisterResult result = registerService.register(request);
        System.out.println(result);
    }
}
