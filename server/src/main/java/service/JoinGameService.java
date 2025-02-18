package service;

public class JoinGameService {
    /*
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
