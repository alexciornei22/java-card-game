package main.card.ability;

import main.card.MinionCard;

public final class WeakKnees implements SpecialAbility {
    @Override
    public void use(final MinionCard attacker, final MinionCard target) {
        target.removeAttackDamage(2);
    }
}
