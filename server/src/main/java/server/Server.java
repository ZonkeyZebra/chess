package server;

import com.google.gson.Gson;
import dataaccess.*;
import model.*;
import service.*;
import spark.*;

import java.util.Collection;

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
    private final CreateGameService createGameService = new CreateGameService(game);
    private final Gson gson = new Gson();

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
        Spark.post("/game", this::createGame);
        Spark.exception(DataAccessException.class, this::exceptionHandler);

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
        if (regRequest.username() == null || regRequest.password() == null || regRequest.email() == null) {
            response.status(400);
            throw new DataAccessException("bad request");
        }
        UserData user = new UserData(regRequest.username(), regRequest.password(), regRequest.email());
        if (user == registerService.getUser(user.username())) {
            response.status(403);
            throw new DataAccessException("already taken");
        }
        RegisterResult result = registerService.register(regRequest);
        response.status(200);
        return gson.toJson(result);
    }

    private Object clear(Request request, Response response) {
        clearService.clear();
        response.status(200);
        return "{}";
    }

    private Object login(Request request, Response response) throws DataAccessException {
        LoginResult result = null;
        LoginRequest loginRequest = gson.fromJson(request.body(), LoginRequest.class);
        response.status(200);
        if (loginService.getUser(loginRequest.username()) == null) {
            response.status(401);
        }
        result = loginService.login(loginRequest);
        return gson.toJson(result);
    }

    private Object logout(Request request, Response response) throws DataAccessException {
        var authToken = request.headers();
        if (authToken == null) {
            response.status(401);
            throw new DataAccessException("Error: unauthorized");
        }
        logoutService.logout();
        response.status(200);
        return "{}";
    }

    private Object joinGame(Request request, Response response) {
        JoinGameRequest joinGameRequest = gson.fromJson(request.body(), JoinGameRequest.class);
        joinGameService.joinGame(joinGameRequest);
        response.status(200);
        return "{}";
    }

    private Object listGames(Request request, Response response) {
        Collection<ListGamesResult> games = listGamesService.listGames();
        response.status(200);
        return gson.toJson(games);
    }

    private Object createGame(Request request, Response response) {
        CreateGameRequest createGameRequest = gson.fromJson(request.body(), CreateGameRequest.class);
        CreateGameResult result = createGameService.createGame(createGameRequest);
        response.status(200);
        return gson.toJson(result);
    }

    private void exceptionHandler(DataAccessException ex, Request req, Response res) {
        //res.status(ex.StatusCode());
        //res.body(ex.toJson());
        gson.toJson(ex);
    }
}
