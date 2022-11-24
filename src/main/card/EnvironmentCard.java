package main.card;

import fileio.CardInput;
import main.Game;
import main.card.ability.RowAbility;

public final class EnvironmentCard extends Card {
    private final RowAbility rowAbility;
    public EnvironmentCard(final CardInput cardInput,
                           final RowAbility rowAbility) {
        super(cardInput);
        this.rowAbility = rowAbility;
    }

    /**
     * @param game instance of Game
     * @param affectedRow the row on which the ability is used
     */
    public void useRowAbility(final Game game, final int affectedRow) {
        rowAbility.use(game, affectedRow);
    }
}
