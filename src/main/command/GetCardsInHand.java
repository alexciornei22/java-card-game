package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.card.Card;
import main.card.EnvironmentCard;
import main.card.MinionCard;

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
        ArrayList<Card> clone = new ArrayList<>();

        if ((playerIdx == 1)) {
            game.getPlayer1().getHand().forEach(card -> {
                if (Game.ENVIRONMENT_CARDS.contains(card.getName())) {
                    clone.add(new Card(card));
                } else {
                    clone.add(new MinionCard((MinionCard) card));
                }
            });
        } else {
            game.getPlayer2().getHand().forEach(card -> {
                if (Game.ENVIRONMENT_CARDS.contains(card.getName())) {
                    clone.add(new Card(card));
                } else {
                    clone.add(new MinionCard((MinionCard) card));
                }
            });
        }

        output.addObject()
                .put("command", "getCardsInHand")
                .put("playerIdx", playerIdx)
                .putPOJO("output", clone);
    }
}
