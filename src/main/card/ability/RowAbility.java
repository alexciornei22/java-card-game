package main.card.ability;

import main.Game;

public interface RowAbility {
    /**
     * @param game instance of Game
     * @param affectedRow row on which the ability is used
     */
    void use(Game game, int affectedRow);
}
