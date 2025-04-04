package server;

import com.google.gson.Gson;
import dataaccess.*;
import exception.DataAccessException;
import model.*;
import server.websocket.WebSocketHandler;
import service.*;
import spark.*;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class Server {
    private final UserDAO user = new MySqlUserDAO();
    private final AuthDAO auth = new MySqlAuthDAO();
    private final GameDAO game = new MySqlGameDAO();
    private final RegisterService registerService = new RegisterService(user, auth);
    private final ClearService clearService = new ClearService(auth, game, user);
    private final LoginService loginService = new LoginService(user, auth);
    private final LogoutService logoutService = new LogoutService(auth);
    private final JoinGameService joinGameService = new JoinGameService(null, 404, game, auth);
    private final ListGamesService listGamesService = new ListGamesService(game, auth);
    private final CreateGameService createGameService = new CreateGameService(game, auth);
    private final Gson gson = new Gson();
    private String authToken;
    //private final WebSocketHandler webSocketHandler;

    public Server() {

    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.webSocket("/ws", WebSocketHandler.class);

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
    /// request.headers("Authorization");

    private Object registerUser(Request request, Response response) throws DataAccessException {
        RegisterRequest regRequest = gson.fromJson(request.body(), RegisterRequest.class);
        RegisterResult result = registerService.register(regRequest);
        response.status(200);
        return gson.toJson(result);
    }

    private Object clear(Request request, Response response) throws DataAccessException {
        clearService.clear();
        response.status(200);
        return "{}";
    }

    private Object login(Request request, Response response) throws DataAccessException, IOException {
        LoginResult result = null;
        LoginRequest loginRequest = gson.fromJson(request.body(), LoginRequest.class);
        result = loginService.login(loginRequest);
        response.status(200);
        return gson.toJson(result);
    }

    private Object logout(Request request, Response response) throws DataAccessException {
        authToken = request.headers("Authorization");
        logoutService.logout(authToken);
        response.status(200);
        return "{}";
    }

    private Object joinGame(Request request, Response response) throws DataAccessException, IOException {
        JoinGameRequest joinGameRequest = gson.fromJson(request.body(), JoinGameRequest.class);
        authToken = request.headers("Authorization");
        joinGameService.joinGame(joinGameRequest, authToken);
        response.status(200);
        return "{}";
    }

    private Object listGames(Request request, Response response) throws DataAccessException {
        authToken = request.headers("Authorization");
        Collection<GameData> gameList = listGamesService.getGames(authToken);
        ListGamesResult result = new ListGamesResult(gameList);
        response.status(200);
        return gson.toJson(result);
    }

    private Object createGame(Request request, Response response) throws DataAccessException {
        authToken = request.headers("Authorization");
        CreateGameRequest createGameRequest = gson.fromJson(request.body(), CreateGameRequest.class);
        CreateGameResult result = createGameService.createGame(createGameRequest, authToken);
        response.status(200);
        return gson.toJson(result);
    }

    private void exceptionHandler(DataAccessException ex, Request req, Response res) {
        if (Objects.equals(ex.getMessage(), "Error: unauthorized")) {
            res.status(401);
        } else if (Objects.equals(ex.getMessage(), "Error: bad request")) {
            res.status(400);
        } else if (Objects.equals(ex.getMessage(), "Error: already taken")) {
            res.status(403);
        } else {
            res.status(500);
        }
        res.body(gson.toJson(Map.of("message", ex.getMessage())));
    }
}
