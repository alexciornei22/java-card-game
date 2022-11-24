package main.card.ability;

import main.card.MinionCard;

public final class Skyjack implements SpecialAbility {
    @Override
    public void use(final MinionCard attacker, final MinionCard target) {
        int temp = attacker.getHealth();
        attacker.setHealth(target.getHealth());
        target.setHealth(temp);
    }
}
