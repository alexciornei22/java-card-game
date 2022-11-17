package main.card.ability;

import main.Game;
import main.card.Card;
import main.card.MinionCard;

import java.util.ArrayList;

public interface RowAbility {
    void use(Game game, int affectedRow);
}