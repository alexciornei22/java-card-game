package main.card.ability;

import main.card.MinionCard;

public interface SpecialAbility {
    public void use(MinionCard attacker, MinionCard target);
}
