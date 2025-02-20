package server;

import spark.*;
import service.ClearService;

public class Server {
    //private final ClearService clearService;

//    public Server() {
//        this.service = service;
//        webSocketHandler = new WebSocketHandler();
//    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        clear();

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    /// authTokens required except for register, login, and clear
//    private Object clear(Request req, Response res) {
//        service.deleteAllPets();
//        res.status(204);
//        return "";
//    }

    private static void clear() {
        Spark.delete("/db", (req, res) -> {
            res.status(200);
            res.type("text/plain");
            res.header("CS240", "Awesome!");
            res.body("Hello BYU!");
            return res.body();
        });
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
