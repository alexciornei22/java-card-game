package main.card;

import fileio.CardInput;
import main.Game;
import main.card.ability.*;

import java.security.PublicKey;

public class HeroCard extends Card {
    int health = 30;
    RowAbility rowAbility;

    public HeroCard(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    public HeroCard(CardInput cardInput) {
        super(cardInput);

        switch (name) {
            case "Lord Royce" -> rowAbility = new SubZero();
            case "Empress Thorina" -> rowAbility = new LowBlow();
            case "King Mudface" -> rowAbility = new EarthBorn();
            case "General Kocioraw" -> rowAbility = new BloodThirst();
        }
    }

    public HeroCard(HeroCard heroCard) {
        super(heroCard);
        this.health = heroCard.getHealth();
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void removeHealth(int health) {
        this.health -= health;
    }

    public void useHeroAbility(Game game, int affectedRow) {
        rowAbility.use(game, affectedRow);
    }
}
