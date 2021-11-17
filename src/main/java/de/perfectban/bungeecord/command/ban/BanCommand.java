package de.perfectban.bungeecord.command.ban;

import de.perfectban.command.CommandInterface;
import de.perfectban.bungeecord.config.ConfigManager;
import de.perfectban.bungeecord.config.ConfigType;
import de.perfectban.command.helper.BanCommandHelper;
import de.perfectban.meta.Config;
import de.perfectban.util.PlaceholderManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BanCommand extends Command implements CommandInterface
{
    private final BanCommandHelper banCommandHelper;

    public BanCommand(String name, String permission) {
        super(name, permission);

        this.banCommandHelper = new BanCommandHelper();
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args.length == 0) {
            // send help
            commandSender.sendMessage(new TextComponent(getDescription()));
            return;
        }

        // the action the user wants to execute
        // none equals banning the player
        String action = args[0];

        // the player/console that issued the action
        UUID moderator = getModerator(commandSender);

        // todo: command parser

        String reason = "";
        String time = "";
        boolean permanent = true;

        if (args.length >= 2 && action.equalsIgnoreCase("info")) {
            String player = args[1];

            banCommandHelper.banInfo(player, message -> commandSender.sendMessage(new TextComponent(message)));
        } else if (args.length >= 2 && action.equalsIgnoreCase("delete")) {
            String player = args[1];

            banCommandHelper.deleteBan(player, reason, moderator, message -> commandSender.sendMessage(new TextComponent(message)));
        } else if (args.length >= 2 && action.equalsIgnoreCase("change")) {
            String player = args[1];

            banCommandHelper.changeBan(player, reason, time, permanent, moderator, message -> commandSender.sendMessage(new TextComponent(message)));
        } else {
            // this should be username or random
            String player = args[0];

            banCommandHelper.ban(player, reason, time, permanent, moderator, message -> commandSender.sendMessage(new TextComponent(message)));
        }
    }

    @Override
    public HashMap<Integer, List<String>> getTabCompletions(CommandSender commandSender, String[] typedArguments) {
        return null;
    }

    @Override
    public String getDescription() {
        return PlaceholderManager.replacePrefix(
            ConfigManager.getString(ConfigType.COMMANDS, Config.COMMAND_BAN_DESCRIPTION)
        );
    }

    private UUID getModerator(CommandSender commandSender) {
        UUID moderator = null;

        if (commandSender instanceof ProxiedPlayer) {
            moderator = ((ProxiedPlayer) commandSender).getUniqueId();
        }

        return moderator;
    }
}
