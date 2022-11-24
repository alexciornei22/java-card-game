package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.card.MinionCard;

import java.util.ArrayList;

public final class GetFrozenCardsOnTable implements Command {
    private final Game game;

    public GetFrozenCardsOnTable(final Game game) {
        this.game = game;
    }

    @Override
    public void execute(final ArrayNode output) {
        ArrayList<MinionCard> cards = new ArrayList<>();
        game.getCardsOnTable().forEach(row -> {
            row.stream()
                    .filter(MinionCard::isFrozen)
                    .forEach(cards::add);
        });

        output.addObject()
                .put("command", "getFrozenCardsOnTable")
                .putPOJO("output", cards);
    }
}
