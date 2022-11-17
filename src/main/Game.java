package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.StartGameInput;
import main.card.MinionCard;
import main.command.*;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    public static final List<String> ENVIRONMENT_CARDS = Collections.unmodifiableList(
            Arrays.asList(
                    "Winterfell",
                    "Firestorm",
                    "Heart Hound"
            )
    );
    public static final List<String> FRONT_ROW_CARDS = Collections.unmodifiableList(
            Arrays.asList(
                    "The Ripper",
                    "Miraj",
                    "Goliath",
                    "Warden"
            )
    );
    public static final List<String> BACK_ROW_CARDS = Collections.unmodifiableList(
            Arrays.asList(
                    "Sentinel",
                    "Berserker",
                    "The Cursed One",
                    "Disciple"
            )
    );

    Player player1;
    Player player2;
    int player1DeckId;
    int player2DeckId;
    int turn = 1;
    int round = 1;
    int playerTurn;
    ArrayList<ArrayList<MinionCard>> table = new ArrayList<ArrayList<MinionCard>>();

    public Game(Player player1, Player player2, StartGameInput startGameInput) {

        this.player1 = player1;
        this.player2 = player2;
        player1DeckId = startGameInput.getPlayerOneDeckIdx();
        player2DeckId = startGameInput.getPlayerTwoDeckIdx();
        playerTurn = startGameInput.getStartingPlayer();

        table.add(new ArrayList<MinionCard>());
        table.add(new ArrayList<MinionCard>());
        table.add(new ArrayList<MinionCard>());
        table.add(new ArrayList<MinionCard>());

        Collections.shuffle(getPlayer1Deck().getCards(), new Random(startGameInput.getShuffleSeed()));
        Collections.shuffle(getPlayer2Deck().getCards(), new Random(startGameInput.getShuffleSeed()));

        player1.addCardToHand(player1DeckId);
        player2.addCardToHand(player2DeckId);
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

    public void endCurrentTurn() {
        if (++turn > 2)
            nextRound();

        playerTurn = (playerTurn == 1)? 2 : 1;
    }

    private void nextRound() {
        round++;
        turn = 1;

        int extraMana = Math.min(round, 10);
        player1.addMana(extraMana);
        player2.addMana(extraMana);

        player1.addCardToHand(player1DeckId);
        player2.addCardToHand(player2DeckId);
    }

    public boolean isRowFull(int row) {
        return table.get(row).size() == 5;
    }

    public boolean rowBelongsToEnemy(int row) {
        if (playerTurn == 1 && (row == 2 || row == 3))
            return false;
        if (playerTurn == 2 && (row == 0 || row == 1))
            return false;
        return true;
    }

    public int getMirrorRow(int row) {
        if (playerTurn == 1)
            return (row == 0)? 3 : 2;
        return (row == 3)? 0 : 1;
    }

    public void placeCardOnTable(MinionCard card, int row) {
        table.get(row).add(card);
    }

    public ArrayList<ArrayList<MinionCard>> getCardsOnTable() {
        return table;
    }

    public ArrayList<MinionCard> getRow(int affectedRow) {
        return table.get(affectedRow);
    }

    public MinionCard getCardAtPosition(int x, int y) {
        try {
            return table.get(x).get(y);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void removeDeadCards() {
        table.forEach(minionCards -> {
            List<MinionCard> deadCards = minionCards.stream()
                    .filter(minionCard -> minionCard.getHealth() <= 0)
                    .toList();
            minionCards.removeAll(deadCards);
        });
    }

    public Command getCommandObject(ActionsInput actionsInput) {
        switch (actionsInput.getCommand()) {
            case "getPlayerDeck":
                return new GetPlayerDeck(this, actionsInput.getPlayerIdx());
            case "getPlayerHero":
                return new GetPlayerHero(this, actionsInput.getPlayerIdx());
            case "getPlayerTurn":
                return new GetPlayerTurn(this);
            case "endPlayerTurn":
                return new EndPlayerTurn(this);
            case "placeCard":
                return new PlaceCard(this, actionsInput.getHandIdx());
            case "getPlayerMana":
                return new GetPlayerMana(this, actionsInput.getPlayerIdx());
            case "getCardsInHand":
                return new GetCardsInHand(this, actionsInput.getPlayerIdx());
            case "getCardsOnTable":
                return new GetCardsOnTable(this);
            case "getFrozenCardsOnTable":
                return new GetFrozenCardsOnTable(this);
            case "useEnvironmentCard":
                return new UseEnvironmentCard(this, actionsInput.getHandIdx(), actionsInput.getAffectedRow());
            case "getEnvironmentCardsInHand":
                return new GetEnvironmentCardsInHand(this, actionsInput.getPlayerIdx());
            case "getCardAtPosition":
                return new GetCardAtPosition(this, actionsInput.getX(), actionsInput.getY());
            default:
                return new Command() {
                    @Override
                    public void execute(ArrayNode output) {
                        output.addObject().put("error", "Command not found");
                    }
                };
        }
    }

}
