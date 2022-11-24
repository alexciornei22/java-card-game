package main.card.ability;

import main.Game;
import main.card.MinionCard;

import java.util.ArrayList;

public final class Firestorm implements RowAbility {
    @Override
    public void use(final Game game, final int affectedRow) {
        ArrayList<MinionCard> cards = game.getRow(affectedRow);
        cards.forEach(card -> card.removeHealth(1));
    }
}
