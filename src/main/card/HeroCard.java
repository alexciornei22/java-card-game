package main.card;

import fileio.CardInput;
import main.Game;
import main.card.ability.*;

public final class HeroCard extends Card {
    public static final int DEFAULT_HEALTH = 30;
    private int health = DEFAULT_HEALTH;
    private RowAbility rowAbility;

    public HeroCard(final int mana,
                    final String description,
                    final String[] colors,
                    final String name) {
        super(mana, description, colors, name);
    }

    public HeroCard(final CardInput cardInput) {
        super(cardInput);

        switch (name) {
            case "Lord Royce" -> setRowAbility(new SubZero());
            case "Empress Thorina" -> setRowAbility(new LowBlow());
            case "King Mudface" -> setRowAbility(new EarthBorn());
            case "General Kocioraw" -> setRowAbility(new BloodThirst());
            default -> { }
        }
    }

    public HeroCard(final HeroCard heroCard) {
        super(heroCard);
        this.health = heroCard.getHealth();
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public void setRowAbility(final RowAbility rowAbility) {
        this.rowAbility = rowAbility;
    }

    /**
     * @param removedHealth points to be removed from card's health
     */
    public void removeHealth(final int removedHealth) {
        this.health -= removedHealth;
    }

    /**
     * @param game instance of Game
     * @param affectedRow the row on which the ability is used
     */
    public void useHeroAbility(final Game game, final int affectedRow) {
        rowAbility.use(game, affectedRow);
    }
}
