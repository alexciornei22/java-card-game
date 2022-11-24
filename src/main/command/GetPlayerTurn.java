package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;

public final class GetPlayerTurn implements Command {
    private final Game game;

    public GetPlayerTurn(final Game game) {
        this.game = game;
    }

    @Override
    public void execute(final ArrayNode output) {
        output.addObject()
                .put("command", "getPlayerTurn")
                .put("output", game.getPlayerTurn());
    }
}
