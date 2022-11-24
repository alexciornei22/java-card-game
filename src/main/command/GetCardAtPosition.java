package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.card.MinionCard;

public final class GetCardAtPosition implements Command {
    private final Game game;
    private final int x;
    private final int y;

    public GetCardAtPosition(final Game game, final int x, final int y) {
        this.game = game;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(final ArrayNode output) {
        MinionCard card = game.getCardAtPosition(x, y);

        output.addObject()
                .put("command", "getCardAtPosition")
                .putPOJO("output", (card != null)
                        ? new MinionCard(card)
                        : "No card available at that position.")
                .put("x", x)
                .put("y", y);
    }
}
