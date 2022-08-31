package com.rail.web.command;

/**
 * @author Andrii Babichev
 * @version 1.0
 * This class is a container of commands.
 * When the application starts, all commands are created and stored in a hashmap
 * @see ContexListener#initServices()
 */

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private Map<String, Command> commands = new HashMap<>();
    public Command getCommand(String name) {
        return commands.get(name);
    }
    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }
    @Override
    public String toString() {
        return "CommandContainer=[" + commands + "]";
    }

}
