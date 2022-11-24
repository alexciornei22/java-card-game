package main.card.ability;

import main.Game;
import main.card.MinionCard;

import java.util.ArrayList;

public final class LowBlow implements RowAbility {
    @Override
    public void use(final Game game, final int affectedRow) {
        ArrayList<MinionCard> cards = game.getRow(affectedRow);

        MinionCard maxHealth = null;
        for (MinionCard card
                : cards) {
            if (maxHealth == null || card.getAttackDamage() >= maxHealth.getAttackDamage()) {
                maxHealth = card;
            }
        }

        cards.remove(maxHealth);
    }
}
