package main;

import fileio.CardInput;
import main.card.Card;
import main.card.EnvironmentCard;
import main.card.MinionCard;
import main.card.ability.Firestorm;
import main.card.ability.HeartHound;
import main.card.ability.Winterfell;

import java.util.ArrayList;

public final class Deck {
    private final ArrayList<Card> cards = new ArrayList<>();

    public Deck(final ArrayList<CardInput> deckInput) {
        deckInput.forEach(cardInput -> {
            if (Game.ENVIRONMENT_CARDS.contains(cardInput.getName())) {
                switch (cardInput.getName()) {
                    case "Firestorm" -> cards.add(
                            new EnvironmentCard(cardInput, new Firestorm())
                    );
                    case "Winterfell" -> cards.add(
                            new EnvironmentCard(cardInput, new Winterfell())
                    );
                    case "Heart Hound" -> cards.add(
                            new EnvironmentCard(cardInput, new HeartHound())
                    );
                    default -> { }
                }
            } else {
                cards.add(new MinionCard(cardInput));
            }
        });
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
