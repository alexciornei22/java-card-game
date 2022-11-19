package main.card.ability;

import main.card.MinionCard;

public class GodsPlan implements SpecialAbility {
    @Override
    public void use(MinionCard attacker, MinionCard target) {
        target.addHealth(2);
    }
}
