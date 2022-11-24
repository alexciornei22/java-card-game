package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;

public interface Command {
    /**
     * Interface used for command classes
    */

    void execute(ArrayNode output);
}
