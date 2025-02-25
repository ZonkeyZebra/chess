package server;

import dataaccess.*;
import model.RegisterRequest;
import model.RegisterResult;
import model.UserData;
import service.RegisterService;

public class Testing {
    public static void main(String[] args) throws DataAccessException {
        UserDAO user = new MemoryUserDAO();
        AuthDAO auth = new MemoryAuthDAO();
        RegisterService registerService = new RegisterService(user, auth);
        RegisterRequest request = new RegisterRequest("angela", "angel", "heaven@above.com");
        RegisterResult result = registerService.register(request);
        System.out.println(result);


    }
}
