package de.perfectban.command;

import de.perfectban.PerfectBan;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Arrays;

public class CommandListener implements Listener
{
    @EventHandler
    public void onCommandExecute(ChatEvent event) {
        Connection connection = event.getSender();
        String message = event.getMessage();

        if (!message.startsWith("/")) {
            return;
        }

        String[] split = message.substring(1).split(" ");


        String label = split[0];
        String[] args = Arrays.copyOfRange(split, 1, split.length);

        // loop all known commands and check if label is in aliases
        for (CommandInterface command : PerfectBan.getInstance().getCommandManager().getCommands()) {
            if (command.getAliases().contains(label.toLowerCase())) {
                command.onCommand((ProxiedPlayer) connection, args, label);
            }
        }
    }
}
