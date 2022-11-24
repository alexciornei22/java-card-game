package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;

public final class GetCardsOnTable implements Command {
    private final Game game;

    public GetCardsOnTable(final Game game) {
        this.game = game;
    }

    @Override
    public void execute(final ArrayNode output) {
        output.addObject()
                .put("command", "getCardsOnTable")
                .putPOJO("output", game.getCardsOnTable());
    }
}
