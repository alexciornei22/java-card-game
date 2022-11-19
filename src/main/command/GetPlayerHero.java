package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.card.HeroCard;

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
                                ? new HeroCard(game.getPlayer1().getHeroCard())
                                : new HeroCard(game.getPlayer2().getHeroCard())
                );
    }
}
