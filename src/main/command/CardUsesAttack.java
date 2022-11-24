package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Coordinates;
import main.Game;
import main.card.MinionCard;

public final class CardUsesAttack implements Command {
    private final Game game;
    private final Coordinates attacker;
    private final Coordinates defender;

    public CardUsesAttack(final Game game,
                          final Coordinates attacker,
                          final Coordinates defender) {
        this.game = game;
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    public void execute(final ArrayNode output) {
        boolean error = false;
        String errorMessage = null;
        if (!game.rowBelongsToEnemy(defender.getX())) {
            error = true;
            errorMessage = "Attacked card does not belong to the enemy.";
        }

        MinionCard card = game.getCardAtPosition(attacker.getX(), attacker.getY());

        if (!error && game.getHaveAttacked().contains(card)) {
            error = true;
            errorMessage = "Attacker card has already attacked this turn.";
        }

        if (card == null || !error && card.isFrozen()) {
            error = true;
            errorMessage = "Attacker card is frozen.";
        }

        if (!error && game.enemyHasTanks()) {
            MinionCard enemy = game.getCardAtPosition(defender.getX(), defender.getY());

            if (enemy == null || !Game.TANK_CARDS.contains(enemy.getName())) {
                error = true;
                errorMessage = "Attacked card is not of type 'Tank'.";
            }
        }

        if (error) {
            output.addObject()
                    .put("command", "cardUsesAttack")
                    .putPOJO("cardAttacker", attacker)
                    .putPOJO("cardAttacked", defender)
                    .put("error", errorMessage);
        } else {
            MinionCard enemy = game.getCardAtPosition(defender.getX(), defender.getY());
            if (enemy == null) {
                return;
            }

            card.attackCard(enemy);
            game.cardHasAttacked(card);
        }
    }
}
