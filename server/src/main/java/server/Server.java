package server;

import model.RegisterResult;
import model.UserData;
import service.RegisterService;
import spark.*;
import service.ClearService;

public class Server {
    private final ClearService clearService;
    private final RegisterService registerService;

    public Server() {
        this.clearService = null;
        this.registerService = null;
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);
        Spark.post("/user", this::registerUser);

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

    private Object registerUser(Request request, Response response) {
        /// registerService.createUser(request); need help with this
        /// registerService.createAuth(); need help with this
        RegisterResult result = null;
        response.status(200);
        response.body("username: " + result.username() + "authToken: " + result.authToken());
        return "";
    }

    /**
     * Clear application
     * property	value
     * Description	Clears the database. Removes all users, games, and authTokens.
     * URL path	/db
     * HTTP Method	DELETE
     * Success response	[200] {}
     * Failure response	[500] { "message": "Error: (description of error)" }
     *
     * Register
     * property	value
     * Description	Register a new user.
     * URL path	/user
     * HTTP Method	POST
     * Body	{ "username":"", "password":"", "email":"" }
     * Success response	[200] { "username":"", "authToken":"" }
     * Failure response	[400] { "message": "Error: bad request" }
     * Failure response	[403] { "message": "Error: already taken" }
     * Failure response	[500] { "message": "Error: (description of error)" }
     *
     * Login
     * property	value
     * Description	Logs in an existing user (returns a new authToken).
     * URL path	/session
     * HTTP Method	POST
     * Body	{ "username":"", "password":"" }
     * Success response	[200] { "username":"", "authToken":"" }
     * Failure response	[401] { "message": "Error: unauthorized" }
     * Failure response	[500] { "message": "Error: (description of error)" }
     *
     * Logout
     * property	value
     * Description	Logs out the user represented by the authToken.
     * URL path	/session
     * HTTP Method	DELETE
     * Headers	authorization: <authToken>
     * Success response	[200] {}
     * Failure response	[401] { "message": "Error: unauthorized" }
     * Failure response	[500] { "message": "Error: (description of error)" }
     *
     * List Games
     * Note that whiteUsername and blackUsername may be null.
     * property	value
     * Description	Gives a list of all games.
     * URL path	/game
     * HTTP Method	GET
     * Headers	authorization: <authToken>
     * Success response	[200] { "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName:""} ]}
     * Failure response	[401] { "message": "Error: unauthorized" }
     * Failure response	[500] { "message": "Error: (description of error)" }
     *
     * Create Game
     * property	value
     * Description	Creates a new game.
     * URL path	/game
     * HTTP Method	POST
     * Headers	authorization: <authToken>
     * Body	{ "gameName":"" }
     * Success response	[200] { "gameID": 1234 }
     * Failure response	[400] { "message": "Error: bad request" }
     * Failure response	[401] { "message": "Error: unauthorized" }
     * Failure response	[500] { "message": "Error: (description of error)" }
     *
     * Join Game
     * property	value
     * Description	Verifies that the specified game exists and adds the caller as the requested color to the game.
     * URL path	/game
     * HTTP Method	PUT
     * Headers	authorization: <authToken>
     * Body	{ "playerColor":"WHITE/BLACK", "gameID": 1234 }
     * Success response	[200] {}
     * Failure response	[400] { "message": "Error: bad request" }
     * Failure response	[401] { "message": "Error: unauthorized" }
     * Failure response	[403] { "message": "Error: already taken" }
     * Failure response	[500] { "message": "Error: (description of error)" }
     */
}
