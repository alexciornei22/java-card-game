package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.StartGameInput;
import main.card.MinionCard;
import main.command.*;

import java.util.*;
import java.util.stream.Collectors;

public final class Game {
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
    public static final List<String> TANK_CARDS = Collections.unmodifiableList(
            Arrays.asList(
                    "Goliath",
                    "Warden"
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
    ArrayList<MinionCard> haveAttacked = new ArrayList<>();

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

    public ArrayList<MinionCard> getHaveAttacked() {
        return haveAttacked;
    }

    public void cardHasAttacked(MinionCard card) {
        this.haveAttacked.add(card);
    }

    public void resetHaveAttacked() {
        this.haveAttacked.clear();
    }

    public void unfreezeCards() {
        if (playerTurn == 1) {
            getRow(2).forEach(card -> card.setFrozen(false));
            getRow(3).forEach(card -> card.setFrozen(false));
        } else {
            getRow(0).forEach(card -> card.setFrozen(false));
            getRow(1).forEach(card -> card.setFrozen(false));
        }
    }

    public void endCurrentTurn() {
        if (++turn > 2)
            nextRound();

        unfreezeCards();
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

    public ArrayList<MinionCard> getRow(int row) {
        return table.get(row);
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

    public boolean rowHasTanks(int row) {
        List<MinionCard> tanks = getRow(row).stream()
                .filter(card -> TANK_CARDS.contains(card.getName()))
                .toList();

        return tanks.size() != 0;
    }

    public boolean enemyHasTanks() {
        if (playerTurn == 1) {
            return rowHasTanks(1);
        }
        return rowHasTanks(2);
    }

    public Command getCommandObject(ActionsInput actionsInput) {
        return switch (actionsInput.getCommand()) {
            case "getPlayerDeck" -> new GetPlayerDeck(this, actionsInput.getPlayerIdx());
            case "getPlayerHero" -> new GetPlayerHero(this, actionsInput.getPlayerIdx());
            case "getPlayerTurn" -> new GetPlayerTurn(this);
            case "endPlayerTurn" -> new EndPlayerTurn(this);
            case "placeCard" -> new PlaceCard(this, actionsInput.getHandIdx());
            case "cardUsesAttack" ->
                    new CardUsesAttack(this, actionsInput.getCardAttacker(), actionsInput.getCardAttacked());
            case "cardUsesAbility" ->
                    new CardUsesAbility(this, actionsInput.getCardAttacker(), actionsInput.getCardAttacked());
            case "getPlayerMana" -> new GetPlayerMana(this, actionsInput.getPlayerIdx());
            case "getCardsInHand" -> new GetCardsInHand(this, actionsInput.getPlayerIdx());
            case "getCardsOnTable" -> new GetCardsOnTable(this);
            case "getFrozenCardsOnTable" -> new GetFrozenCardsOnTable(this);
            case "useEnvironmentCard" ->
                    new UseEnvironmentCard(this, actionsInput.getHandIdx(), actionsInput.getAffectedRow());
            case "getEnvironmentCardsInHand" -> new GetEnvironmentCardsInHand(this, actionsInput.getPlayerIdx());
            case "getCardAtPosition" -> new GetCardAtPosition(this, actionsInput.getX(), actionsInput.getY());
            default -> new Command() {
                @Override
                public void execute(ArrayNode output) {
                    output.addObject().put("error", "Command not found: " + actionsInput.getCommand());
                }
            };
        };
    }

}
