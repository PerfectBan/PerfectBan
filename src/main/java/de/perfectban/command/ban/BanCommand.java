package de.perfectban.command.ban;

import de.perfectban.PerfectBan;
import de.perfectban.command.CommandInterface;
import de.perfectban.config.ConfigManager;
import de.perfectban.config.ConfigType;
import de.perfectban.entity.Ban;
import de.perfectban.entity.repository.BanRepository;
import de.perfectban.util.TimeManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.apache.commons.cli.*;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BanCommand extends Command implements CommandInterface
{
    private final BanRepository repository
            = new BanRepository(PerfectBan.getInstance().getEntityManager());

    public BanCommand(String name) {
        super(name);
    }

    public BanCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args.length == 0) {
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

            // todo: remove debug

            if (args.length >= 2 && action.equalsIgnoreCase("ban")) {
                String player = args[1];
                ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(player);

                if (proxiedPlayer == null) {
                    commandSender.sendMessage(new TextComponent(ConfigManager.getConfig(ConfigType.MESSAGES)
                            .getString("perfectban.error.player_not_found")));
                    return;
                }

                // calculate until
                long diff = TimeManager.convertToMillis(time);
                Timestamp until = new Timestamp(System.currentTimeMillis() + diff);

                List<Ban> bans = repository.getBans(proxiedPlayer.getUniqueId());

                if (!bans.isEmpty()) {
                    commandSender.sendMessage(new TextComponent(ConfigManager.getConfig(ConfigType.MESSAGES)
                            .getString("perfectban.error.player_already_fined")));
                    return;
                }

                // ban player
                Ban ban = repository.createBan(proxiedPlayer.getUniqueId(), reason, until, lifetime, false);

                // broadcast to moderators
                if (ConfigManager.getConfig(ConfigType.SETTINGS).getBoolean("useBroadcast")) {
                    // todo: remove debug
                    ProxyServer.getInstance().broadcast(new
                        TextComponent(String.format("§cPlayer §e%s§c was banned for §e%s", proxiedPlayer.getDisplayName(), ban.getReason())));
                }

                commandSender.sendMessage(new TextComponent(ConfigManager.getConfig(ConfigType.MESSAGES).getString("perfectban.ban.command.player_banned")));
            } else if (action.equalsIgnoreCase("info")) {
                int id = Integer.parseInt(args[1]);
                Ban ban = repository.getBan(id);

                if (ban == null) {
                    // todo: send message
                    return;
                }
            } else if (action.equalsIgnoreCase("delete")) {

            } else if (action.equalsIgnoreCase("change")) {

            } else {
                // this should be username or random
            }
        } catch (ParseException exception) {
            // todo: error
            commandSender.sendMessage(new TextComponent("todo: error"));
        }
    }

    @Override
    public HashMap<Integer, List<String>> getTabCompletions(CommandSender commandSender, String[] typedArguments) {
        return null;
    }

    @Override
    public String getDescription() {
        return ConfigManager.getConfig(ConfigType.MESSAGES).getString("perfectban.ban.command.description");
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
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
