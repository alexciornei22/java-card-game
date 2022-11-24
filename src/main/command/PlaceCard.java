package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.Player;
import main.card.Card;
import main.card.MinionCard;

public final class PlaceCard implements Command {
    private final Game game;
    private final int handIdx;

    public PlaceCard(final Game game, final int handIdx) {
        this.game = game;
        this.handIdx = handIdx;
    }

    @Override
    public void execute(final ArrayNode output) {
        Player player = (game.getPlayerTurn() == 1) ? game.getPlayer1() : game.getPlayer2();

        Card card = player.getHand().get(handIdx);

        boolean error = false;
        String errorMessage = null;
        if (!card.canBePlacedOnTable()) {
            error = true;
            errorMessage = "Cannot place environment card on table.";
        }

        if (player.getMana() < card.getMana()) {
            error = true;
            errorMessage = "Not enough mana to place card on table.";
        }

        int row = Card.getCardRow(card, game.getPlayerTurn());
        if (game.isRowFull(row)) {
            error = true;
            errorMessage = "Cannot place card on table since row is full.";
        }

        if (error) {
            output.addObject()
                    .put("command", "placeCard")
                    .put("handIdx", handIdx)
                    .put("error", errorMessage);
        } else {
            player.getHand().remove(handIdx);
            player.removeMana(card.getMana());
            game.placeCardOnTable((MinionCard) card, row);
        }
    }
}
