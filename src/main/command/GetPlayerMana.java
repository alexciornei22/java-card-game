package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;
import main.Player;

public class GetPlayerMana implements Command {
    Game game;
    int playerIdx;

    public GetPlayerMana(Game game, int playerIdx) {
        this.game = game;
        this.playerIdx = playerIdx;
    }

    @Override
    public void execute(ArrayNode output) {
        Player player = (playerIdx == 1)? game.getPlayer1() : game.getPlayer2();

        output.addObject()
                .put("command", "getPlayerMana")
                .put("playerIdx", playerIdx)
                .put("output", player.getMana());
    }
}
