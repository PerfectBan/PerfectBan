package de.perfectban.command.ban;

import de.perfectban.PerfectBan;
import de.perfectban.command.CommandInterface;
import de.perfectban.entity.Ban;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

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

    @Override
    public boolean isPlayerOnly() {
        return false;
    }
}
