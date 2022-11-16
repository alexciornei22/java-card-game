package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.card.Card;

import java.util.ArrayList;

public class GetCardsOnTable implements Command {
    Game game;

    public GetCardsOnTable(Game game) {
        this.game = game;
    }

    @Override
    public void execute(ArrayNode output) {
        output.addObject()
                .put("command", "getCardsOnTable")
                .putPOJO("output", game.getCardsOnTable());
    }
}
