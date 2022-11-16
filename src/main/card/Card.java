package main.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fileio.CardInput;
import main.Game;

@JsonIgnoreProperties({"frozen"})
public class Card {
    int mana;
    String description;
    String[] colors;
    String name;

    public Card(int mana, String description, String[] colors, String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }

    public Card(CardInput cardInput) {
        this.mana = cardInput.getMana();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors().toArray(new String[0]);
        this.name = cardInput.getName();
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean canBePlacedOnTable() {
        return false;
    }

    public static int getCardRow(Card card, int playerIdx) {
        if (Game.FRONT_ROW_CARDS.contains(card.getName())) {
            return (playerIdx == 1)? 2 : 1;
        } else
            return (playerIdx == 1)? 3 : 0;
    }
}
