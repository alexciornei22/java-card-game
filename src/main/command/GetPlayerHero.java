package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.card.HeroCard;

public final class GetPlayerHero implements Command {
    private final Game game;
    private final int playerIdx;

    public GetPlayerHero(final Game game, final int playerIdx) {
        this.game = game;
        this.playerIdx = playerIdx;
    }

    @Override
    public void execute(final ArrayNode output) {
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
