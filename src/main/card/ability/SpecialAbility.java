package main.card.ability;

import main.card.MinionCard;

public interface SpecialAbility {
    /**
     * @param attacker card using ability
     * @param target card on which the ability is used
     */
    void use(MinionCard attacker, MinionCard target);
}
