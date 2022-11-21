package main.card.ability;

import main.Game;
import main.card.MinionCard;

import java.util.ArrayList;

public class SubZero implements RowAbility {
    @Override
    public void use(Game game, int affectedRow) {
        ArrayList<MinionCard> cards = game.getRow(affectedRow);

        MinionCard maxHealth = cards.get(0);
        for (MinionCard card :
            cards) {
            if (card.getAttackDamage() > maxHealth.getAttackDamage())
                maxHealth = card;
        }

        maxHealth.setFrozen(true);
    }
}
