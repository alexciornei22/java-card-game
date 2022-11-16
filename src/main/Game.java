package main;

import fileio.StartGameInput;
import main.card.Card;
import main.card.MinionCard;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
    public static final List<String> ENVIRONMENT_CARDS = Collections.unmodifiableList(
            Arrays.asList(
                    "Winterfell",
                    "Firestorm",
                    "Heart Hound"
            )
    );

    Player player1;
    Player player2;
    int player1DeckId;
    int player2DeckId;
    int turn = 1;
    int playerTurn;
    MinionCard[][] table = new MinionCard[4][5];

    public Game(Player player1, Player player2, StartGameInput startGameInput) {

        this.player1 = player1;
        this.player2 = player2;
        player1DeckId = startGameInput.getPlayerOneDeckIdx();
        player2DeckId = startGameInput.getPlayerTwoDeckIdx();
        playerTurn = startGameInput.getStartingPlayer();

        Collections.shuffle(getPlayer1Deck().getCards(), new Random(startGameInput.getShuffleSeed()));
        Collections.shuffle(getPlayer2Deck().getCards(), new Random(startGameInput.getShuffleSeed()));

        player1.getHand().add(getPlayer1Deck().getCards().remove(0));
        player2.getHand().add(getPlayer2Deck().getCards().remove(0));
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Deck getPlayer1Deck() {
        return this.player1.getDecks().get(player1DeckId);
    }

    public Deck getPlayer2Deck() {
        return this.player2.getDecks().get(player2DeckId);
    }

    public int getTurn() {
        return turn;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }
}
