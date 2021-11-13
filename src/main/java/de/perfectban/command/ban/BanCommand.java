package de.perfectban.command.ban;

import de.perfectban.command.CommandInterface;
import net.md_5.bungee.api.CommandSender;

import java.util.HashMap;
import java.util.List;

public class BanCommand implements CommandInterface
{
    @Override
    public void onCommand(CommandSender commandSender, String[] args, String label) {

    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public HashMap<Integer, List<String>> getTabCompletions(CommandSender commandSender, String[] typedArguments) {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
