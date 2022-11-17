package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.card.MinionCard;

import java.util.ArrayList;

public class GetFrozenCardsOnTable implements Command {
    Game game;

    public GetFrozenCardsOnTable(Game game) {
        this.game = game;
    }

    @Override
    public void execute(ArrayNode output) {
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
