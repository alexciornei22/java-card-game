package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Statistics;

public final class GetPlayerTwoWins implements Command {
    @Override
    public void execute(final ArrayNode output) {
        output.addObject()
                .put("command", "getPlayerTwoWins")
                .put("output", Statistics.getInstance().getPlayer2Wins());
    }
}
