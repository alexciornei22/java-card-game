package main.card.ability;

import main.Game;
import main.card.MinionCard;

import java.util.ArrayList;

public class Winterfell implements RowAbility {
    @Override
    public void use(Game game, int affectedRow) {
        ArrayList<MinionCard> cards = game.getRow(affectedRow);
        cards.forEach(card -> card.setFrozen(true));
    }
}
