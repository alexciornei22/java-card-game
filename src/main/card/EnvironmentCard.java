package main.card;

import fileio.CardInput;
import main.Game;
import main.card.ability.RowAbility;

public class EnvironmentCard extends Card {
    RowAbility rowAbility;
    public EnvironmentCard(CardInput cardInput, RowAbility rowAbility) {
        super(cardInput);
        this.rowAbility = rowAbility;
    }

    public void useRowAbility(Game game, int affectedRow) {
        rowAbility.use(game, affectedRow);
    }
}
