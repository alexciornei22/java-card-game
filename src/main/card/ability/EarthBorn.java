package main.card.ability;

import main.Game;
import main.card.MinionCard;

import java.util.ArrayList;

public class EarthBorn implements RowAbility {
    @Override
    public void use(Game game, int affectedRow) {
        ArrayList<MinionCard> cards = game.getRow(affectedRow);
        cards.forEach(card -> card.addHealth(1));
    }
}