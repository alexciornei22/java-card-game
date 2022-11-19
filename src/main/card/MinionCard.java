package main.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import fileio.CardInput;
import main.card.ability.*;

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

        switch (name) {
            case "The Ripper" -> specialAbility = new WeakKnees();
            case "Miraj" -> specialAbility = new Skyjack();
            case "The Cursed One" -> specialAbility = new Shapeshift();
            case "Disciple" -> specialAbility = new GodsPlan();
            default -> {
            }
        }
    }

    public MinionCard(MinionCard card) {
        super(card);
        health = card.health;
        attackDamage = card.attackDamage;
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

    public void addHealth(int health) {
        this.health += health;
    }
    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void removeAttackDamage(int attackDamage) {
        this.attackDamage -= attackDamage;
        if (this.attackDamage < 0)
            this.attackDamage = 0;
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

    public void attackCard(MinionCard card) {
        card.removeHealth(attackDamage);
    }

    @Override
    public boolean canBePlacedOnTable() {
        return true;
    }
}
