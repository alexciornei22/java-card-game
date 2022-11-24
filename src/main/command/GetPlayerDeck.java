package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.card.Card;

import java.util.ArrayList;

public final class GetPlayerDeck implements Command {
    private final Game game;
    private final int playerIdx;

    public GetPlayerDeck(final Game game, final int playerIdx) {
        this.game = game;
        this.playerIdx = playerIdx;
    }

    @Override
    public void execute(final ArrayNode output) {
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
