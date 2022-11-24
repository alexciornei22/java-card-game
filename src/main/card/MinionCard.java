package main.card;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import fileio.CardInput;
import main.card.ability.*;

@JsonPropertyOrder({"mana", "attackDamage", "health", "description", "colors", "name"})
public final class MinionCard extends Card {
    private int health;
    private int attackDamage;
    private boolean frozen = false;
    private SpecialAbility specialAbility;

    public MinionCard(final CardInput cardInput) {
        super(cardInput);
        this.health = cardInput.getHealth();
        this.attackDamage = cardInput.getAttackDamage();

        switch (name) {
            case "The Ripper" -> setSpecialAbility(new WeakKnees());
            case "Miraj" -> setSpecialAbility(new Skyjack());
            case "The Cursed One" -> setSpecialAbility(new Shapeshift());
            case "Disciple" -> setSpecialAbility(new GodsPlan());
            default -> { }
        }
    }

    public MinionCard(final MinionCard card) {
        super(card);
        health = card.health;
        attackDamage = card.attackDamage;
    }

    public void setSpecialAbility(final SpecialAbility specialAbility) {
        this.specialAbility = specialAbility;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * @param health health to be added to the card
     */
    public void addHealth(final int addedHealth) {
        this.health += addedHealth;
    }
    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * @param attackDamage attackDamage to be added to the card
     */
    public void addAttackDamage(final int addedAttackDamage) {
        this.attackDamage += addedAttackDamage;
    }

    /**
     * @param attackDamage points to be removed from the card's attackDamage
     */
    public void removeAttackDamage(final int removedAttackDamage) {
        this.attackDamage -= removedAttackDamage;
        if (this.attackDamage < 0) {
            this.attackDamage = 0;
        }
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * @param health health to be removed from card
     */
    public void removeHealth(final int removedHealth) {
        this.health -= removedHealth;
    }

    /**
     * @param target the card on which the ability should be used
     */
    public void useSpecialAbility(final MinionCard target) {
        this.specialAbility.use(this, target);
    }

    /**
     * @param card card to be attacked
     */
    public void attackCard(final MinionCard card) {
        card.removeHealth(attackDamage);
    }

    /**
     * @param heroCard hero to be attacked
     */
    public void attackHero(final HeroCard heroCard) {
        heroCard.removeHealth(attackDamage);
    }
    @Override
    public boolean canBePlacedOnTable() {
        return true;
    }
}
