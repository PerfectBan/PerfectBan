package de.perfectban.command.ban;

import de.perfectban.PerfectBan;
import de.perfectban.command.CommandInterface;
import de.perfectban.entity.Ban;
import de.perfectban.entity.repository.BanRepository;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import org.apache.commons.cli.*;

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
        String action = args[0];

        try {
            CommandLine commandLine = parseCommandLineArguments(args);

            // arguments
            String reason = commandLine.getOptionValue("r");
            String time = commandLine.getOptionValue("t");
            boolean lifetime = commandLine.hasOption("lt");
            boolean ip = commandLine.hasOption("ip");

            if (action.equalsIgnoreCase("info")) {
                int id = Integer.parseInt(args[1]);
                Ban ban = repository.getBan(id);

                if (ban == null) {
                    // todo: send message
                    return;
                }

                // todo: send success message
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
        return "perfectban.ban.command.description"; // todo: configurable;
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    private CommandLine parseCommandLineArguments(String[] args) throws ParseException {
        Options options = new Options();
        CommandLineParser parser = new BasicParser();

        options.addOption(new Option("r", "reason", false, "The reason for the ban"));
        options.addOption(new Option("t", "time", false, "The time a player will be banned"));
        options.addOption(new Option("lt", "lifetime", false, "Ban forever"));
        options.addOption(new Option("ip", "ipban", false, "IP-Ban"));

        return parser.parse(options, args);
    }
}
