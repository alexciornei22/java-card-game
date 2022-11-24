package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Statistics;

public class GetPlayerTwoWins implements Command {
    @Override
    public void execute(ArrayNode output) {
        output.addObject()
                .put("command", "getPlayerTwoWins")
                .put("output", Statistics.getInstance().getPlayer2Wins());
    }
}
