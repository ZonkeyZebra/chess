package service;

import chess.ChessGame;
import dataaccess.*;
import exception.DataAccessException;
import model.AuthData;
import model.GameData;
import model.JoinGameRequest;

import java.util.Objects;

public class JoinGameService {
    private ChessGame.TeamColor playerColor;
    private int gameID;
    private GameDAO gameDataAccess = new MemoryGameDAO();
    private AuthDAO authDataAccess = new MySqlAuthDAO();
    private GameData game;

    public JoinGameService(ChessGame.TeamColor playerColor, int gameID, GameDAO gameDataAccess, AuthDAO authDataAccess) {
        this.playerColor = playerColor;
        this.gameID = gameID;
        this.gameDataAccess = gameDataAccess;
        this.authDataAccess = authDataAccess;
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
                    updateGame(authToken, userAuth, game.whiteUsername(), username);
                } else if (game.blackUsername().isEmpty()) {
                    updateGame(authToken, userAuth, game.whiteUsername(), username);
                } else {
                    throw new DataAccessException("Error: already taken");
                }
            } else if (playerColor == ChessGame.TeamColor.WHITE) {
                if (game.whiteUsername() == null) {
                    updateGame(authToken, userAuth, username, game.blackUsername());
                } else if (game.whiteUsername().isEmpty()) {
                    updateGame(authToken, userAuth, username, game.blackUsername());
                } else {
                    throw new DataAccessException("Error: already taken");
                }
            }
        }
    }

    private void updateGame(String authToken, String userAuth, String whiteUser, String blackUser) throws DataAccessException {
        if (Objects.equals(authToken, userAuth)) {
            gameDataAccess.updateGame(new GameData(game.gameID(), whiteUser, blackUser, game.gameName(), game.game()));
        }
    }

    public boolean gameExists(int gameID) throws DataAccessException {
        game = gameDataAccess.getGame(gameID);
        return game.gameID() == gameID;
    }
}
