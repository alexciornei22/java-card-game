package main;

import fileio.CardInput;
import fileio.DecksInput;
import main.card.Card;
import main.card.EnvironmentCard;
import main.card.MinionCard;

import java.util.ArrayList;

public class Deck {
    ArrayList<Card> cards = new ArrayList<>();

    public Deck(ArrayList<CardInput> deckInput) {
        deckInput.forEach(cardInput -> {
            if (Game.ENVIRONMENT_CARDS.contains(cardInput.getName())) {
                cards.add(new EnvironmentCard(cardInput));
            } else {
                cards.add(new MinionCard(cardInput));
            }
        });
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
