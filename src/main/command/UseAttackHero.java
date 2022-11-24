package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Coordinates;
import main.Game;
import main.Statistics;
import main.card.MinionCard;

public final class UseAttackHero implements Command {
    private final Game game;
    private final Coordinates attacker;

    public UseAttackHero(final Game game, final Coordinates attacker) {
        this.game = game;
        this.attacker = attacker;
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

        if (game.enemyHasTanks()) {
            error = true;
            errorMessage = "Attacked card is not of type 'Tank'.";
        }

        if (error) {
            output.addObject()
                    .put("command", "useAttackHero")
                    .putPOJO("cardAttacker", attacker)
                    .put("error", errorMessage);
        } else {
            card.attackHero(game.getEnemyPlayer().getHeroCard());
            game.cardHasAttacked(card);

            if (game.isGameOver()) {
                Statistics statistics = Statistics.getInstance();
                statistics.addWin(game.getPlayerTurn());

                output.addObject()
                        .put("gameEnded", "Player "
                                + ((game.getPlayerTurn() == 1) ? "one" : "two")
                                + " killed the enemy hero.");
            }
        }
    }
}
