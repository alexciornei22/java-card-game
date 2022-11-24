package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.Player;
import main.card.Card;
import main.card.EnvironmentCard;

public final class UseEnvironmentCard implements Command {
    private final Game game;
    private final int handIdx;
    private final int affectedRow;

    public UseEnvironmentCard(final Game game, final int handIdx, final int affectedRow) {
        this.game = game;
        this.handIdx = handIdx;
        this.affectedRow = affectedRow;
    }

    @Override
    public void execute(final ArrayNode output) {
        Player player = (game.getPlayerTurn() == 1) ? game.getPlayer1() : game.getPlayer2();

        Card card = player.getHand().get(handIdx);

        boolean error = false;
        String errorMessage = null;

        if (!game.rowBelongsToEnemy(affectedRow)) {
            error = true;
            errorMessage = "Chosen row does not belong to the enemy.";
        }

        if (player.getMana() < card.getMana()) {
            error = true;
            errorMessage = "Not enough mana to use environment card.";
        }


        if (card.getName().equals("Heart Hound")
                && game.isRowFull(game.getMirrorRow(affectedRow))) {
            error = true;
            errorMessage = "Cannot steal enemy card since the player's row is full.";
        }

        if (!Game.ENVIRONMENT_CARDS.contains(card.getName())) {
            error = true;
            errorMessage = "Chosen card is not of type environment.";
        }

        if (error) {
            output.addObject()
                    .put("command", "useEnvironmentCard")
                    .put("handIdx", handIdx)
                    .put("affectedRow", affectedRow)
                    .put("error", errorMessage);
        } else {
            EnvironmentCard environmentCard = (EnvironmentCard) card;
            environmentCard.useRowAbility(game, affectedRow);
            player.getHand().remove(card);
            player.removeMana(card.getMana());
        }
    }
}
