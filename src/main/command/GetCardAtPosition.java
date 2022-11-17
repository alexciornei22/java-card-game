package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.card.Card;
import main.card.MinionCard;

public class GetCardAtPosition implements Command {
    Game game;
    int x;
    int y;

    public GetCardAtPosition(Game game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(ArrayNode output) {
        MinionCard card = game.getCardAtPosition(x, y);

        output.addObject()
                .put("command", "getCardAtPosition")
                .putPOJO("output", (card != null) ? new MinionCard(card) : "No card available at that position.");
    }
}
