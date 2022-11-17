package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.Player;
import main.card.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetEnvironmentCardsInHand implements Command {
    Game game;
    int playerIdx;

    public GetEnvironmentCardsInHand(Game game, int playerIdx) {
        this.game = game;
        this.playerIdx = playerIdx;
    }

    @Override
    public void execute(ArrayNode output) {
        Player player = (game.getPlayerTurn() == 1)? game.getPlayer1() : game.getPlayer2();

        List<Card> envCards = player.getHand().stream()
                .filter(card -> Game.ENVIRONMENT_CARDS.contains(card.getName()))
                .toList();

        output.addObject()
                .put("command", "getEnvironmentCardsInHand")
                .put("playerIdx", playerIdx)
                .putPOJO("output", envCards);
    }
}
