package service;

import chess.ChessGame;
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
    private UserDAO userDataAccess;
    private GameData game;

    public JoinGameService(ChessGame.TeamColor playerColor, int gameID, GameDAO gameDataAccess) {
        this.playerColor = playerColor;
        this.gameID = gameID;
        this.gameDataAccess = gameDataAccess;
    }

    public void joinGame(JoinGameRequest joinGameRequest, String authToken) {
        gameID = joinGameRequest.gameID();
        playerColor = joinGameRequest.playerColor();
        game = gameDataAccess.getGame(gameID);
        String userAuth = "";
        String username = "";
        if (gameExists(gameID)) {
            if (playerColor == ChessGame.TeamColor.BLACK) {
                if (game.blackUsername() == null) {
                    if (Objects.equals(authToken, userAuth)) {
                        gameDataAccess.updateGame(new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game()));
                    }
                }
            } else if (playerColor == ChessGame.TeamColor.WHITE) {
                if (game.whiteUsername() == null) {
                    if (Objects.equals(authToken, userAuth)) {
                        gameDataAccess.updateGame(new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game()));
                    }
                }
            }
        }
    }

    public boolean gameExists(int gameID) {
        game = gameDataAccess.getGame(gameID);
        return game.gameID() == gameID;
    }
}
