package main;

import fileio.DecksInput;
import main.card.Card;
import main.card.HeroCard;

import java.util.ArrayList;

public final class Player {
    ArrayList<Deck> decks = new ArrayList<>();
    ArrayList<Card> hand = new ArrayList<>();
    HeroCard heroCard;
    int mana = 1;

    public Player(DecksInput decksInput) {
        decksInput.getDecks().forEach(deckInput -> decks.add(new Deck(deckInput)));
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void addMana(int mana) {
        this.mana += mana;
    }

    public void removeMana(int mana) {
        this.mana -= mana;
    }

    public HeroCard getHeroCard() {
        return heroCard;
    }

    public void setHeroCard(HeroCard heroCard) {
        this.heroCard = heroCard;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addCardToHand(int deckIdx) {
        if (!decks.get(deckIdx).getCards().isEmpty())
        hand.add(decks.get(deckIdx).getCards().remove(0));
    }
}
