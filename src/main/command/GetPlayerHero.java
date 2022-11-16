package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;

public class GetPlayerHero implements Command {
    Game game;
    int playerIdx;

    public GetPlayerHero(Game game, int playerIdx) {
        this.game = game;
        this.playerIdx = playerIdx;
    }

    @Override
    public void execute(ArrayNode output) {
        output.addObject()
                .put("command", "getPlayerHero")
                .put("playerIdx", playerIdx)
                .putPOJO("output",
                        (playerIdx == 1)
                                ? game.getPlayer1().getHeroCard()
                                : game.getPlayer2().getHeroCard()
                );
    }
}
