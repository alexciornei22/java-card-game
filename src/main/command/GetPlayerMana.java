package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.Player;

public final class GetPlayerMana implements Command {
    private final Game game;
    private final int playerIdx;

    public GetPlayerMana(final Game game, final int playerIdx) {
        this.game = game;
        this.playerIdx = playerIdx;
    }

    @Override
    public void execute(final ArrayNode output) {
        Player player = (playerIdx == 1) ? game.getPlayer1() : game.getPlayer2();

        output.addObject()
                .put("command", "getPlayerMana")
                .put("playerIdx", playerIdx)
                .put("output", player.getMana());
    }
}
