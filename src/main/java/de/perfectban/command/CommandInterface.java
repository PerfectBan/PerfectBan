package de.perfectban.command;

import java.util.HashMap;
import java.util.List;

public interface CommandInterface
{
    HashMap<Integer, List<String>> getTabCompletions(String[] typedArguments);

    String getDescription();
}
