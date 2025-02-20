package server;

import spark.*;
import service.ClearService;

public class Server {
    private final ClearService clearService;

    public Server() {
        this.clearService = null;
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    /// authTokens required except for register, login, and clear
    private Object clear(Request request, Response response) {
        if (clearService != null) {
            clearService.clear();
            response.status(200);
        }
        return "";
    }
}
