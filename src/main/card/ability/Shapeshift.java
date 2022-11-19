package main.card.ability;

import main.card.MinionCard;

public class Shapeshift implements SpecialAbility {
    @Override
    public void use(MinionCard attacker, MinionCard target) {
        int temp = target.getHealth();
        target.setHealth(target.getAttackDamage());
        target.setAttackDamage(temp);
    }
}
