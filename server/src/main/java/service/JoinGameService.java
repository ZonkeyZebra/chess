package service;

import chess.ChessGame;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import model.UserData;

public class JoinGameService {
    private ChessGame.TeamColor playerColor;
    private int gameID;
    private final GameDAO gameDataAccess;
    GameData game;
    AuthData auth;
    UserData user;

    public JoinGameService(ChessGame.TeamColor playerColor, int gameID, GameDAO gameDataAccess) {
        this.playerColor = playerColor;
        this.gameID = gameID;
        this.gameDataAccess = gameDataAccess;
    }

    public void joinGame(ChessGame.TeamColor playerColor, int gameID) {
        if (gameExists(gameID)) {
            if (playerColor == ChessGame.TeamColor.BLACK) {
                gameDataAccess.updateGame(game);
            }
        }
    }

    public boolean gameExists(int gameID) {
        game = gameDataAccess.getGame(gameID);
        return game.gameID() == gameID;
    }
}
