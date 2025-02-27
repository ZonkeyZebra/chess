package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.GameData;
import model.JoinGameRequest;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class JoinGameService {
    private ChessGame.TeamColor playerColor;
    private int gameID;
    private final GameDAO gameDataAccess;
    private AuthDAO authDataAccess;
    private UserDAO userDataAccess;
    private GameData game;

    public JoinGameService(ChessGame.TeamColor playerColor, int gameID, GameDAO gameDataAccess, AuthDAO authDataAccess, UserDAO userDataAccess) {
        this.playerColor = playerColor;
        this.gameID = gameID;
        this.gameDataAccess = gameDataAccess;
        this.authDataAccess = authDataAccess;
        this.userDataAccess = userDataAccess;
    }

    public void joinGame(JoinGameRequest joinGameRequest, String authToken) throws DataAccessException {
        if (authToken == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        if (joinGameRequest.gameID() == null) {
            throw new DataAccessException("Error: bad request");
        }
        gameID = joinGameRequest.gameID();
        playerColor = joinGameRequest.playerColor();
        if (playerColor == null) {
            throw new DataAccessException("Error: bad request");
        }
        game = gameDataAccess.getGame(gameID);
        AuthData authData = authDataAccess.getAuth(authToken);
        String userAuth;
        String username;
        if (authData == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        userAuth = authData.authToken();
        username = authData.username();
        if (gameExists(gameID)) {
            if (playerColor == ChessGame.TeamColor.BLACK) {
                if (game.blackUsername() == null) {
                    if (Objects.equals(authToken, userAuth)) {
                        gameDataAccess.updateGame(new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game()));
                    }
                } else {
                    throw new DataAccessException("Error: already taken");
                }
            } else if (playerColor == ChessGame.TeamColor.WHITE) {
                if (game.whiteUsername() == null) {
                    if (Objects.equals(authToken, userAuth)) {
                        gameDataAccess.updateGame(new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game()));
                    }
                } else {
                    throw new DataAccessException("Error: already taken");
                }
            }
        }
    }

    public boolean gameExists(int gameID) {
        game = gameDataAccess.getGame(gameID);
        return game.gameID() == gameID;
    }
}
