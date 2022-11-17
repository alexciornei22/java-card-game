package main.card.ability;

import main.Game;
import main.card.MinionCard;

import java.util.ArrayList;
import java.util.Comparator;

public class HeartHound implements RowAbility {
    @Override
    public void use(Game game, int affectedRow) {
        ArrayList<MinionCard> cards = game.getRow(affectedRow);
        MinionCard maxHealth = cards.stream().max(new Comparator<MinionCard>() {
            @Override
            public int compare(MinionCard o1, MinionCard o2) {
                return 0;
            }
        }).get();

        cards.remove(maxHealth);
        game.placeCardOnTable(maxHealth, game.getMirrorRow(affectedRow));
    }
}
