package de.perfectban.command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager
{
    private final List<CommandInterface> commands;

    public CommandManager() {
        this.commands = new ArrayList<>();
    }

    public void addCommand(CommandInterface command) {
        this.commands.add(command);
    }

    public List<CommandInterface> getCommands() {
        return commands;
    }
}
