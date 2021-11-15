package de.perfectban.command;

import net.md_5.bungee.api.CommandSender;

import java.util.HashMap;
import java.util.List;

public interface CommandInterface
{
    HashMap<Integer, List<String>> getTabCompletions(CommandSender commandSender, String[] typedArguments);

    String getDescription();

    boolean isPlayerOnly();
}
