package main.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fileio.CardInput;
import main.Game;

@JsonIgnoreProperties({"frozen"})
public class Card {
    private int mana;
    private String description;
    private String[] colors;
    protected String name;

    public Card(final int mana,
                final String description,
                final String[] colors,
                final String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }

    public Card(final CardInput cardInput) {
        this.mana = cardInput.getMana();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors().toArray(new String[0]);
        this.name = cardInput.getName();
    }

    public Card() { }

    public Card(final Card card) {
        mana = card.getMana();
        description = card.getDescription();
        colors = card.getColors();
        name = card.getName();
    }

    /**
     * @return card's mana point
     */
    public int getMana() {
        return mana;
    }

    /**
     * @param mana the mana value to be set
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * @return card's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description of the card
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return card's array of colors
     */
    public String[] getColors() {
        return colors;
    }

    /**
     * @param colors array of colors
     */
    public void setColors(final String[] colors) {
        this.colors = colors;
    }

    /**
     * @return card's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name a card name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return true if card is a minion, false if not
     */
    public boolean canBePlacedOnTable() {
        return false;
    }

    /**
     * @param card a card that can be placed on the table
     * @param playerIdx payer index (1 or 2)
     * @return the row on which the card should be placed
     */
    public static int getCardRow(final Card card, final int playerIdx) {
        if (Game.FRONT_ROW_CARDS.contains(card.getName())) {
            return (playerIdx == 1) ? 2 : 1;
        } else {
            return (playerIdx == 1) ? Game.PLAYER_ONE_BACK_ROW : 0;
        }
    }

    /**
     * @return String containing the card's name, mana and description
     */
    @Override
    public String toString() {
        return "Card{"
                + name + ","
                + mana + ","
                + description;
    }
}
