package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.card.Card;

import java.util.ArrayList;

public class GetCardsInHand implements Command {
    Game game;
    int playerIdx;

    public GetCardsInHand(Game game, int playerIdx) {
        this.game = game;
        this.playerIdx = playerIdx;
    }

    @Override
    public void execute(ArrayNode output) {
        output.addObject()
                .put("command", "getCardsInHand")
                .put("playerIdx", playerIdx)
                .putPOJO("output",
                        (playerIdx == 1)
                                ? new ArrayList<Card>(game.getPlayer1().getHand())
                                : new ArrayList<Card>(game.getPlayer2().getHand())
                );
    }
}
