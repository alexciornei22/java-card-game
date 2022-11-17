package main.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import fileio.CardInput;
import main.card.ability.SpecialAbility;

@JsonPropertyOrder({"mana", "attackDamage", "health", "description", "colors", "name"})
public class MinionCard extends Card {
    int health;
    int attackDamage;
    boolean frozen = false;
    SpecialAbility specialAbility;

    public MinionCard(CardInput cardInput) {
        super(cardInput);
        this.health = cardInput.getHealth();
        this.attackDamage = cardInput.getAttackDamage();
    }

    public MinionCard(MinionCard card) {
        super();
        health = card.health;
        attackDamage = card.attackDamage;
        name = card.name;
        description = card.description;
        colors = card.colors;
        mana = card.mana;
    }

    public void setSpecialAbility(SpecialAbility specialAbility) {
        this.specialAbility = specialAbility;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public void removeHealth(int health) {
        this.health -= health;
    }

    public void useSpecialAbility(MinionCard target) {
        this.specialAbility.use(this, target);
    }

    @Override
    public boolean canBePlacedOnTable() {
        return true;
    }
}
