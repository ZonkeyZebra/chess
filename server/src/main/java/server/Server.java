package server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.*;
import model.*;
import service.*;
import spark.*;

import java.util.Collection;
import java.util.StringTokenizer;

public class Server {
    private final UserDAO user = new MemoryUserDAO();
    private final AuthDAO auth = new MemoryAuthDAO();
    private final GameDAO game = new MemoryGameDAO();
    private final RegisterService registerService = new RegisterService(user, auth);
    private final ClearService clearService = new ClearService(auth, game, user);
    private final LoginService loginService = new LoginService(user, auth);
    private final LogoutService logoutService = new LogoutService(auth);
    private final JoinGameService joinGameService = new JoinGameService(null, 404, game);
    private final ListGamesService listGamesService = new ListGamesService(game);
    private final Gson gson = new Gson();
    private String authToken = null;

    public Server() {

    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);
        Spark.post("/user", this::registerUser);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.put("/game", this::joinGame);
        Spark.get("/game", this::listGames);

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

    private Object registerUser(Request request, Response response) throws DataAccessException {
        RegisterRequest regRequest = gson.fromJson(request.body(), RegisterRequest.class);
        RegisterResult result = registerService.register(regRequest);
        response.status(200);
        return gson.toJson(result);
    }

    private Object clear(Request request, Response response) {
        clearService.clear();
        response.status(200);
        return "{}";
    }

    private Object login(Request request, Response response) {
        LoginRequest loginRequest = gson.fromJson(request.body(), LoginRequest.class);
        LoginResult result = loginService.login(loginRequest);
        response.status(200);
        return gson.toJson(result);
    }

    private Object logout(Request request, Response response) {
        var authToken = request.headers();
        logoutService.logout();
        response.status(200);
        return "{}";
    }

    private Object joinGame(Request request, Response response) {
        JoinGameRequest joinGameRequest = gson.fromJson(request.body(), JoinGameRequest.class);
        joinGameService.joinGame(joinGameRequest.playerColor(), joinGameRequest.gameID());
        response.status(200);
        return "{}";
    }

    private Object listGames(Request request, Response response) {
        Collection<ListGamesResult> games = listGamesService.listGames();
        response.status(200);
        return gson.toJson(games);
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
