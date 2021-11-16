package de.perfectban.command.ban;

import de.perfectban.PerfectBan;
import de.perfectban.command.CommandInterface;
import de.perfectban.config.ConfigManager;
import de.perfectban.config.ConfigType;
import de.perfectban.entity.Ban;
import de.perfectban.entity.repository.BanRepository;
import de.perfectban.meta.Config;
import de.perfectban.util.PlaceholderManager;
import de.perfectban.util.TimeManager;
import de.perfectban.util.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import org.apache.commons.cli.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class BanCommand extends Command implements CommandInterface
{
    private final BanRepository repository
            = new BanRepository(PerfectBan.getInstance().getEntityManager());

    public BanCommand(String name, String permission) {
        super(name, permission);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args.length == 0) {
            // send help
            commandSender.sendMessage(new TextComponent(getDescription()));
            return;
        }

        String action = args[0];

        try {
            CommandLine commandLine = parseCommandLineArguments(args);

            // arguments
            String reason = commandLine.getOptionValue("r");
            String time = commandLine.getOptionValue("t");
            boolean lifetime = commandLine.hasOption("lt");
            boolean ip = commandLine.hasOption("ip");

            if (args.length >= 2 && action.equalsIgnoreCase("info")) {
                String player = args[1];

                UUIDFetcher.getUUIDbyName(player, uuid -> {
                    if (uuid == null) {
                        commandSender.sendMessage(new TextComponent(PlaceholderManager.replacePrefix(
                            ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_PLAYER_NOT_FOUND))
                        ));
                        return;
                    }

                    List<Ban> bans = repository.getBans(uuid);

                    if (bans.isEmpty()) {
                        commandSender.sendMessage(new TextComponent(PlaceholderManager.replacePrefix(
                                ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_NOT_BANNED))
                        ));
                        return;
                    }

                    String message = PlaceholderManager.replaceBanPlaceholders(ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_TEMPLATE_INFO), bans.get(0));

                    commandSender.sendMessage(new TextComponent(PlaceholderManager.replaceBanPlaceholders(message, bans.get(0))));
                });
            } else if (args.length >= 2 && action.equalsIgnoreCase("delete")) {
                String player = args[1];

                UUIDFetcher.getUUIDbyName(player, uuid -> {
                    if (uuid == null) {
                        commandSender.sendMessage(new TextComponent(PlaceholderManager.replacePrefix(
                                ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_PLAYER_NOT_FOUND))
                        ));
                        return;
                    }

                    List<Ban> bans = repository.getBans(uuid);

                    if (bans.isEmpty()) {
                        commandSender.sendMessage(new TextComponent(PlaceholderManager.replacePrefix(
                            ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_NOT_BANNED)
                        )));
                        return;
                    }

                    Ban ban = bans.get(0);

                    // soft delete ban
                    repository.deleteBan(ban.getId());

                    // broadcast to moderators
                    if (ConfigManager.getBoolean(ConfigType.CONFIG, "useBroadcast")) {
                        String message = PlaceholderManager.replaceBanPlaceholders(
                            ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BROADCAST_DELETE), ban
                        );
                        commandSender.sendMessage(new TextComponent(message));
                        return;
                    }

                    commandSender.sendMessage(new TextComponent(ConfigManager.getString(ConfigType.MESSAGES, "perfectban.ban.command.player_unbanned")));
                });
            } else if (args.length >= 2 && action.equalsIgnoreCase("change")) {
                String player = args[1];

                UUIDFetcher.getUUIDbyName(player, uuid -> {
                    if (uuid == null) {
                        commandSender.sendMessage(new TextComponent(PlaceholderManager.replacePrefix(
                                ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_PLAYER_NOT_FOUND)
                        )));
                        return;
                    }

                    List<Ban> bans = repository.getBans(uuid);

                    if (bans.isEmpty()) {
                        commandSender.sendMessage(new TextComponent(PlaceholderManager.replacePrefix(
                                ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_NOT_BANNED)
                        )));
                        return;
                    }

                    Ban ban = bans.get(0);

                    // calculate until
                    long diff = TimeManager.convertToMillis(time);

                    if (diff != 0) {
                        Timestamp until = new Timestamp(System.currentTimeMillis() + diff);

                        // soft delete ban
                        repository.editBan(ban.getId(), reason, until, lifetime);
                    } else {
                        // soft delete ban
                        repository.editBan(ban.getId(), reason, null, lifetime);
                    }

                    // broadcast to moderators
                    if (ConfigManager.getBoolean(ConfigType.CONFIG, "useBroadcast")) {
                        commandSender.sendMessage(new TextComponent(PlaceholderManager.replaceBanPlaceholders(
                            ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BROADCAST_CHANGE), ban
                        )));
                        return;
                    }

                    commandSender.sendMessage(new TextComponent(ConfigManager.getString(ConfigType.MESSAGES, "perfectban.ban.command.ban_edited")));
                });
            } else if (args.length >= 2) {
                // this should be username or random
                String player = args[0];

                UUIDFetcher.getUUIDbyName(player, uuid -> {
                    if (uuid == null) {
                        commandSender.sendMessage(new TextComponent(PlaceholderManager.replacePrefix(
                                ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_PLAYER_NOT_FOUND)
                        )));
                        return;
                    }

                    // check if reason and time is set
                    if (reason == null || time == null) {
                        commandSender.sendMessage(new TextComponent(ConfigManager.getString(ConfigType.MESSAGES, "perfectban.error.ban.arguments")));
                        return;
                    }

                    // calculate until
                    long diff = TimeManager.convertToMillis(time);
                    Timestamp until = new Timestamp(System.currentTimeMillis() + diff);

                    List<Ban> bans = repository.getBans(uuid);

                    if (!bans.isEmpty()) {
                        commandSender.sendMessage(new TextComponent(PlaceholderManager.replacePrefix(
                                ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_BANNED)
                        )));
                        return;
                    }

                    // ban player
                    Ban ban = repository.createBan(uuid, reason, until, lifetime, false);

                    // broadcast to moderators
                    if (ConfigManager.getBoolean(ConfigType.CONFIG, "useBroadcast")) {
                        commandSender.sendMessage(new TextComponent(PlaceholderManager.replaceBanPlaceholders(
                            ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BROADCAST_CREATE), ban
                        )));
                        return;
                    }

                    String message = PlaceholderManager
                        .replaceBanPlaceholders(ConfigManager.getString(ConfigType.MESSAGES, "perfectban.ban.command.player_banned"), ban);

                    commandSender.sendMessage(new TextComponent(message));
                });
            } else {
                // send help
                commandSender.sendMessage(new TextComponent(getDescription()));
            }
        } catch (ParseException exception) {
            commandSender.sendMessage(new TextComponent(ConfigManager.getString(ConfigType.MESSAGES, "perfectban.error.internal")));
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

    private CommandLine parseCommandLineArguments(String[] args) throws ParseException {
        Options options = new Options();
        CommandLineParser parser = new BasicParser();

        options.addOption(new Option("r", "reason", true, "The reason for the ban"));
        options.addOption(new Option("t", "time", true, "The time a player will be banned"));
        options.addOption(new Option("lt", "lifetime", false, "Ban forever"));
        options.addOption(new Option("ip", "ipban", false, "IP-Ban"));

        return parser.parse(options, args);
    }
}
