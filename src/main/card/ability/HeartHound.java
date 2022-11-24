package main.card.ability;

import main.Game;
import main.card.MinionCard;

import java.util.ArrayList;

public final class HeartHound implements RowAbility {
    @Override
    public void use(final Game game, final int affectedRow) {
        ArrayList<MinionCard> cards = game.getRow(affectedRow);
        MinionCard maxHealth = cards.stream().max((o1, o2) -> 0).get();

        cards.remove(maxHealth);
        game.placeCardOnTable(maxHealth, game.getMirrorRow(affectedRow));
    }
}
