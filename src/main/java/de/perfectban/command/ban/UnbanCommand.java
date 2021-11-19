package de.perfectban.command.ban;

import de.perfectban.command.CommandAction;
import de.perfectban.config.ConfigManager;
import de.perfectban.config.ConfigType;
import de.perfectban.command.CommandArguments;
import de.perfectban.command.CommandInterface;
import de.perfectban.command.CommandParser;
import de.perfectban.command.helper.BanCommandHelper;
import de.perfectban.meta.Config;
import de.perfectban.meta.Placeholder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UnbanCommand extends Command implements CommandInterface
{
    private final CommandParser commandParser;
    private final BanCommandHelper banCommandHelper;

    public UnbanCommand(String name, String permission) {
        super(name, permission);

        this.commandParser = new CommandParser();
        this.banCommandHelper = new BanCommandHelper();
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args.length == 0) {
            // send help
            commandSender.sendMessage(new TextComponent(getDescription()));
            return;
        }

        // parse command arguments
        CommandArguments arguments = commandParser.getArguments(args);

        // the name of the player
        arguments.setPlayer(args[0]);

        // the player/console that issued the action
        arguments.setModerator(getModerator(commandSender));

        // try to unban player
        banCommandHelper.execute(CommandAction.DELETE, arguments, commandSender::sendMessage);
    }

    @Override
    public HashMap<Integer, List<String>> getTabCompletions(String[] typedArguments) {
        return null;
    }

    @Override
    public String getDescription() {
        return Placeholder.replace(
                ConfigManager.getString(ConfigType.COMMANDS, Config.COMMAND_UNBAN_DESCRIPTION),
                new HashMap<>()
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
