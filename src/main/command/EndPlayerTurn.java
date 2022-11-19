package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Game;

public class EndPlayerTurn implements Command {
    Game game;

    public EndPlayerTurn(Game game) {
        this.game = game;
    }

    @Override
    public void execute(ArrayNode output) {
        game.endCurrentTurn();
        game.resetHaveAttacked();
        game.removeDeadCards();
    }
}
