package main.command;

import com.fasterxml.jackson.databind.node.ArrayNode;

public interface Command {
    public void execute(ArrayNode output);
}
