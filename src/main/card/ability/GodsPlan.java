package main.card.ability;

import main.card.MinionCard;

public final class GodsPlan implements SpecialAbility {
    @Override
    public void use(final MinionCard attacker, final MinionCard target) {
        target.addHealth(2);
    }
}
