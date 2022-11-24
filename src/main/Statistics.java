package main;

public class Statistics {
    private static final Statistics instance = new Statistics();
    int gamesPlayed = 0;
    int player1Wins = 0;
    int player2Wins = 0;

    private Statistics() {}

    public static Statistics getInstance() {
        return instance;
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

    public void addWin(int player) {
        if (player == 1)
            player1Wins += 1;
        else
            player2Wins += 1;

        gamesPlayed += 1;
    }

    public void reset() {
        gamesPlayed = 0;
        player1Wins = 0;
        player2Wins = 0;
    }
}
