package main.card;

import fileio.CardInput;

public class HeroCard extends Card {
    int health = 30;

    public HeroCard(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    public HeroCard(CardInput cardInput) {
        super(cardInput);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
