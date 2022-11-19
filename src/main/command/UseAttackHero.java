package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Coordinates;
import main.Game;
import main.card.MinionCard;

public class UseAttackHero implements Command {
    Game game;
    Coordinates attacker;

    public UseAttackHero(Game game, Coordinates attacker) {
        this.game = game;
        this.attacker = attacker;
    }

    @Override
    public void execute(ArrayNode output) {
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
                output.addObject()
                        .put("gameEnded", "Player " +
                                ((game.getPlayerTurn() == 1)? "one" : "two") +
                                " killed the enemy hero.");
            }
        }
    }
}
