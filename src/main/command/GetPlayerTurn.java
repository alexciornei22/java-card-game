package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;

public class GetPlayerTurn implements Command {
    Game game;

    public GetPlayerTurn(Game game) {
        this.game = game;
    }

    @Override
    public void execute(ArrayNode output) {
        output.addObject()
                .put("command", "getPlayerTurn")
                .put("output", game.getPlayerTurn());
    }
}
