package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Statistics;

public final class GetPlayerOneWins implements Command {
    @Override
    public void execute(final ArrayNode output) {
        output.addObject()
                .put("command", "getPlayerOneWins")
                .put("output", Statistics.getInstance().getPlayer1Wins());
    }
}
