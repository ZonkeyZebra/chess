package service;

public class ListGamesService {
    /*
     * Description	Gives a list of all games.
     * URL path	/game
     * HTTP Method	GET
     * Headers	authorization: <authToken>
     * Success response	[200] { "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName:""} ]}
     * Failure response	[401] { "message": "Error: unauthorized" }
     * Failure response	[500] { "message": "Error: (description of error)" }
     * Note that whiteUsername and blackUsername may be null
     */
}
