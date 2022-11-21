package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Deck;
import main.Game;
import main.card.Card;

import java.util.ArrayList;

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
                                ? new ArrayList<Card>(game.getPlayer1Deck().getCards())
                                : new ArrayList<Card>(game.getPlayer2Deck().getCards())
                );
    }
}
