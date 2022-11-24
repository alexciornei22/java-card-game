package main;

public final class Statistics {
    /**
     * Singleton class for the statistics of the played games
     */

    private static final Statistics INSTANCE = new Statistics();
    private int gamesPlayed = 0;
    private int player1Wins = 0;
    private int player2Wins = 0;

    private Statistics() { }

    public static Statistics getInstance() {
        return INSTANCE;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getPlayer1Wins() {
        return player1Wins;
    }

    public int getPlayer2Wins() {
        return player2Wins;
    }

    /**
     * @param player player who won
     */
    public void addWin(final int player) {
        if (player == 1) {
            player1Wins += 1;
        } else {
            player2Wins += 1;
        }

        gamesPlayed += 1;
    }

    /**
     * resets statistics
     */
    public void reset() {
        gamesPlayed = 0;
        player1Wins = 0;
        player2Wins = 0;
    }
}
