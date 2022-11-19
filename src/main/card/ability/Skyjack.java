package main.card.ability;

import main.card.MinionCard;

public class Skyjack implements SpecialAbility {
    @Override
    public void use(MinionCard attacker, MinionCard target) {
        int temp = attacker.getHealth();
        attacker.setHealth(target.getHealth());
        target.setHealth(temp);
    }
}
