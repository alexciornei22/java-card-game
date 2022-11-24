package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.StartGameInput;
import main.card.Card;

import main.card.MinionCard;
import main.command.*;

import java.util.*;

public final class Game {
    public static final int MAX_MANA_ADDED_PER_TURN = 10;
    public static final int MAX_CARDS_PER_ROW = 5;
    public static final int PLAYER_ONE_BACK_ROW = 3;

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

    private final Player player1;
    private final Player player2;
    private final int player1DeckId;
    private final int player2DeckId;
    private int turn = 1;
    private int round = 1;
    private int playerTurn;
    private final ArrayList<ArrayList<MinionCard>> table = new ArrayList<ArrayList<MinionCard>>();
    private final ArrayList<Card> haveAttacked = new ArrayList<>();

    public Game(final Player player1,
                final Player player2,
                final StartGameInput startGameInput) {
        this.player1 = player1;
        this.player2 = player2;
        player1DeckId = startGameInput.getPlayerOneDeckIdx();
        player2DeckId = startGameInput.getPlayerTwoDeckIdx();
        playerTurn = startGameInput.getStartingPlayer();

        table.add(new ArrayList<MinionCard>());
        table.add(new ArrayList<MinionCard>());
        table.add(new ArrayList<MinionCard>());
        table.add(new ArrayList<MinionCard>());

        Collections.shuffle(
                getPlayer1Deck().getCards(),
                new Random(startGameInput.getShuffleSeed())
        );
        Collections.shuffle(
                getPlayer2Deck().getCards(),
                new Random(startGameInput.getShuffleSeed())
        );

        player1.addCardToHand(player1DeckId);
        player2.addCardToHand(player2DeckId);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getEnemyPlayer() {
        return (playerTurn == 1) ? player2 : player1;
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

    public ArrayList<Card> getHaveAttacked() {
        return haveAttacked;
    }

    /**
     * @param card Card which has attacked
     */
    public void cardHasAttacked(final Card card) {
        this.haveAttacked.add(card);
    }

    /**
     * clear the lists of cards that have attacked this turn
     */
    public void resetHaveAttacked() {
        this.haveAttacked.clear();
    }

    /**
     * unfreezes cards at the end of a turn
     */
    public void unfreezeCards() {
        if (playerTurn == 1) {
            getRow(2).forEach(card -> card.setFrozen(false));
            getRow(PLAYER_ONE_BACK_ROW).forEach(card -> card.setFrozen(false));
        } else {
            getRow(0).forEach(card -> card.setFrozen(false));
            getRow(1).forEach(card -> card.setFrozen(false));
        }
    }

    /**
     * unfreezes cards,
     * changes the current player,
     * adds mana to each player if the round has ended
     */
    public void endCurrentTurn() {
        if (++turn > 2) {
            nextRound();
        }

        unfreezeCards();
        playerTurn = (playerTurn == 1) ? 2 : 1;
    }

    private void nextRound() {
        round++;
        turn = 1;

        int extraMana = Math.min(round, MAX_MANA_ADDED_PER_TURN);
        player1.addMana(extraMana);
        player2.addMana(extraMana);

        player1.addCardToHand(player1DeckId);
        player2.addCardToHand(player2DeckId);
    }

    /**
     * @param row a row on the table [0-3]
     * @return true if the row is full, false if not
     */
    public boolean isRowFull(final int row) {
        return table.get(row).size() == MAX_CARDS_PER_ROW;
    }

    /**
     * @param row a row on the table [0-3]
     * @return true if the row belongs to the enemy, false if not
     */
    public boolean rowBelongsToEnemy(final int row) {
        if (playerTurn == 1 && (row == 2 || row == PLAYER_ONE_BACK_ROW)) {
            return false;
        }
        if (playerTurn == 2 && (row == 0 || row == 1)) {
            return false;
        }
        return true;
    }

    /**
     * @param row a row on the table [0-3]
     * @return the opposite row, belonging to the enemy
     */
    public int getMirrorRow(final int row) {
        if (playerTurn == 1) {
            return (row == 0) ? PLAYER_ONE_BACK_ROW : 2;
        }
        return (row == PLAYER_ONE_BACK_ROW) ? 0 : 1;
    }

    /**
     * @param card card to be placed on the table
     * @param row the row on which it should be placed
     */
    public void placeCardOnTable(final MinionCard card, final int row) {
        table.get(row).add(card);
    }

    /**
     * @return ArrayList of the rows on the table
     */
    public ArrayList<ArrayList<MinionCard>> getCardsOnTable() {
        return table;
    }

    /**
     * @param row a row on the table [0-3]
     * @return ArrayList of cards in a row
     */
    public ArrayList<MinionCard> getRow(final int row) {
        return table.get(row);
    }

    /**
     * @param x x position on table
     * @param y y position on table
     * @return the Card found at the specified position on the table
     */
    public MinionCard getCardAtPosition(final int x, final int y) {
        try {
            return table.get(x).get(y);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * removes the cards with <= 0 health from the table
     */
    public void removeDeadCards() {
        table.forEach(minionCards -> {
            List<MinionCard> deadCards = minionCards.stream()
                    .filter(minionCard -> minionCard.getHealth() <= 0)
                    .toList();
            minionCards.removeAll(deadCards);
        });
    }

    /**
     * @param row a row on the table [0-3]
     * @return true if the row has tank cards
     */
    public boolean rowHasTanks(final int row) {
        List<MinionCard> tanks = getRow(row).stream()
                .filter(card -> TANK_CARDS.contains(card.getName()))
                .toList();

        return tanks.size() != 0;
    }

    /**
     * @return true if the enemy has tank cards
     */
    public boolean enemyHasTanks() {
        if (playerTurn == 1) {
            return rowHasTanks(1);
        }
        return rowHasTanks(2);
    }

    /**
     * @return true if the game is over (a hero is dead)
     */
    public boolean isGameOver() {
        return player1.getHeroCard().getHealth() <= 0 || player2.getHeroCard().getHealth() <= 0;
    }

//    public void resetCards() {
//        player1.getHand().forEach(card -> getPlayer1Deck().getCards().add(card));
//        player2.getHand().forEach(card -> getPlayer2Deck().getCards().add(card));
//
//        table.get(3).forEach(card -> getPlayer1Deck().getCards().add(card));
//        table.get(2).forEach(card -> getPlayer1Deck().getCards().add(card));
//        table.get(1).forEach(card -> getPlayer2Deck().getCards().add(card));
//        table.get(0).forEach(card -> getPlayer2Deck().getCards().add(card));
//
//        player1.getHand().clear();
//        player2.getHand().clear();
//    }

    /**
     * @param actionsInput action input object from fileio
     * @return the command object to be executed
     */
    public Command getCommandObject(final ActionsInput actionsInput) {
        return switch (actionsInput.getCommand()) {
            case "getPlayerDeck" -> new GetPlayerDeck(this, actionsInput.getPlayerIdx());
            case "getPlayerHero" -> new GetPlayerHero(this, actionsInput.getPlayerIdx());
            case "getPlayerTurn" -> new GetPlayerTurn(this);
            case "endPlayerTurn" -> new EndPlayerTurn(this);
            case "placeCard" -> new PlaceCard(this, actionsInput.getHandIdx());
            case "cardUsesAttack" ->
                    new CardUsesAttack(
                            this,
                            actionsInput.getCardAttacker(),
                            actionsInput.getCardAttacked());
            case "cardUsesAbility" ->
                    new CardUsesAbility(
                            this,
                            actionsInput.getCardAttacker(),
                            actionsInput.getCardAttacked());
            case "useAttackHero" -> new UseAttackHero(this, actionsInput.getCardAttacker());
            case "useHeroAbility" -> new UseHeroAbility(this, actionsInput.getAffectedRow());
            case "getPlayerMana" -> new GetPlayerMana(this, actionsInput.getPlayerIdx());
            case "getCardsInHand" -> new GetCardsInHand(this, actionsInput.getPlayerIdx());
            case "getCardsOnTable" -> new GetCardsOnTable(this);
            case "getFrozenCardsOnTable" -> new GetFrozenCardsOnTable(this);
            case "useEnvironmentCard" ->
                    new UseEnvironmentCard(
                            this,
                            actionsInput.getHandIdx(),
                            actionsInput.getAffectedRow());
            case "getEnvironmentCardsInHand" -> new GetEnvironmentCardsInHand(
                    this,
                    actionsInput.getPlayerIdx());
            case "getCardAtPosition" -> new GetCardAtPosition(
                    this,
                    actionsInput.getX(),
                    actionsInput.getY());
            case "getTotalGamesPlayed" -> new GetTotalGamesPlayed();
            case "getPlayerOneWins" -> new GetPlayerOneWins();
            case "getPlayerTwoWins" -> new GetPlayerTwoWins();
            default -> new Command() {
                @Override
                public void execute(final ArrayNode output) {
                    output.addObject().put(
                            "error",
                            "Command not found: " + actionsInput.getCommand());
                }
            };
        };
    }

}
