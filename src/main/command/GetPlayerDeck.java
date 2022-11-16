package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;

public class GetPlayerDeck implements Command {
    Game game;
    int playerIdx;

    public GetPlayerDeck(Game game, int playerIdx) {
        this.game = game;
        this.playerIdx = playerIdx;
    }

    @Override
    public void execute(ArrayNode output) {
        output.addObject()
                .put("command", "getPlayerDeck")
                .put("playerIdx", playerIdx)
                .putPOJO("output",
                        (playerIdx == 1)
                                ? game.getPlayer1Deck().getCards()
                                : game.getPlayer2Deck().getCards()
                );
    }
}