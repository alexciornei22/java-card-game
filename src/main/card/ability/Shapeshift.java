package main.card.ability;

import main.card.MinionCard;

public final class Shapeshift implements SpecialAbility {
    @Override
    public void use(final MinionCard attacker, final MinionCard target) {
        int temp = target.getHealth();
        target.setHealth(target.getAttackDamage());
        target.setAttackDamage(temp);
    }
}
