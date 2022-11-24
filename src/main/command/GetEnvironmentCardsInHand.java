package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.Player;
import main.card.Card;

import java.util.List;

public final class GetEnvironmentCardsInHand implements Command {
    private final Game game;
    private final int playerIdx;

    public GetEnvironmentCardsInHand(final Game game, final int playerIdx) {
        this.game = game;
        this.playerIdx = playerIdx;
    }

    @Override
    public void execute(final ArrayNode output) {
        Player player = (game.getPlayerTurn() == 1) ? game.getPlayer1() : game.getPlayer2();

        List<Card> envCards = player.getHand().stream()
                .filter(card -> Game.ENVIRONMENT_CARDS.contains(card.getName()))
                .toList();

        output.addObject()
                .put("command", "getEnvironmentCardsInHand")
                .put("playerIdx", playerIdx)
                .putPOJO("output", envCards);
    }
}
