package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;

public final class EndPlayerTurn implements Command {
    private final Game game;

    public EndPlayerTurn(final Game game) {
        this.game = game;
    }

    @Override
    public void execute(final ArrayNode output) {
        game.endCurrentTurn();
        game.resetHaveAttacked();
        game.removeDeadCards();
    }
}
