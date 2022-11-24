package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.Player;
import main.card.HeroCard;

public final class UseHeroAbility implements Command {
    private final Game game;
    private final int affectedRow;

    public UseHeroAbility(final Game game, final int affectedRow) {
        this.game = game;
        this.affectedRow = affectedRow;
    }

    @Override
    public void execute(final ArrayNode output) {
        boolean error = false;
        String errorMessage = null;
        Player player = (game.getPlayerTurn() == 1) ? game.getPlayer1() : game.getPlayer2();
        HeroCard hero = player.getHeroCard();

        if (player.getMana() < hero.getMana()) {
            error = true;
            errorMessage = "Not enough mana to use hero's ability.";
        }

        if (!error && game.getHaveAttacked().contains(hero)) {
            error = true;
            errorMessage = "Hero has already attacked this turn.";
        }

        if (hero.getName().equals("Lord Royce") || hero.getName().equals("Empress Thorina")) {
            if (!error && !game.rowBelongsToEnemy(affectedRow)) {
                error = true;
                errorMessage = "Selected row does not belong to the enemy.";
            }
        } else {
            if (!error && game.rowBelongsToEnemy(affectedRow)) {
                error = true;
                errorMessage = "Selected row does not belong to the current player.";
            }
        }

        if (error) {
            output.addObject()
                    .put("command", "useHeroAbility")
                    .putPOJO("affectedRow", affectedRow)
                    .put("error", errorMessage);
        } else {
            hero.useHeroAbility(game, affectedRow);
            player.removeMana(hero.getMana());
            game.getHaveAttacked().add(hero);
        }
    }
}
