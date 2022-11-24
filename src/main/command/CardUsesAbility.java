package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Coordinates;
import main.Game;
import main.card.MinionCard;

public final class CardUsesAbility implements Command {
    private final Game game;
    private final Coordinates attacker;
    private final Coordinates defender;

    public CardUsesAbility(final Game game,
                           final Coordinates attacker,
                           final Coordinates defender) {
        this.game = game;
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    public void execute(final ArrayNode output) {
        MinionCard card = game.getCardAtPosition(attacker.getX(), attacker.getY());
        boolean error = false;
        String errorMessage = null;

        if (card == null || card.isFrozen()) {
            error = true;
            errorMessage = "Attacker card is frozen.";
        }

        if (!error && game.getHaveAttacked().contains(card)) {
            error = true;
            errorMessage = "Attacker card has already attacked this turn.";
        }

        MinionCard target = game.getCardAtPosition(defender.getX(), defender.getY());

        if (card != null && card.getName().equals("Disciple")) {
            if (game.rowBelongsToEnemy(defender.getX())) {
                error = true;
                errorMessage = "Attacked card does not belong to the current player.";
            }
        } else {
            if (!game.rowBelongsToEnemy(defender.getX())) {
                error = true;
                errorMessage = "Attacked card does not belong to the enemy.";
            } else if (target != null
                    && game.enemyHasTanks()
                    && !Game.TANK_CARDS.contains(target.getName())) {
                error = true;
                errorMessage = "Attacked card is not of type 'Tank'.";
            }
        }

        if (error) {
            output.addObject()
                    .put("command", "cardUsesAbility")
                    .putPOJO("cardAttacker", attacker)
                    .putPOJO("cardAttacked", defender)
                    .put("error", errorMessage);
        } else {
            card.useSpecialAbility(target);
            game.cardHasAttacked(card);
        }
    }
}
