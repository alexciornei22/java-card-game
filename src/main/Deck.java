package main;

import fileio.CardInput;
import fileio.DecksInput;
import main.card.Card;
import main.card.EnvironmentCard;
import main.card.MinionCard;
import main.card.ability.Firestorm;
import main.card.ability.HeartHound;
import main.card.ability.Winterfell;

import java.util.ArrayList;

public class Deck {
    ArrayList<Card> cards = new ArrayList<>();

    public Deck(ArrayList<CardInput> deckInput) {
        deckInput.forEach(cardInput -> {
            if (Game.ENVIRONMENT_CARDS.contains(cardInput.getName())) {
                switch (cardInput.getName()) {
                    case "Firestorm":
                        cards.add(new EnvironmentCard(cardInput, new Firestorm()));
                        break;
                    case "Winterfell":
                        cards.add(new EnvironmentCard(cardInput, new Winterfell()));
                        break;
                    case "Heart Hound":
                        cards.add(new EnvironmentCard(cardInput, new HeartHound()));
                        break;
                }
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
