package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Statistics;

public class GetTotalGamesPlayed implements Command {
    @Override
    public void execute(ArrayNode output) {
        output.addObject()
                .put("command", "getTotalGamesPlayed")
                .put("output", Statistics.getInstance().getGamesPlayed());
    }
}
