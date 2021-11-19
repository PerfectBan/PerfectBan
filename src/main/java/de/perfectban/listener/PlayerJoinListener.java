package de.perfectban.listener;

import de.perfectban.PerfectBan;
import de.perfectban.config.ConfigManager;
import de.perfectban.config.ConfigType;
import de.perfectban.database.entity.Ban;
import de.perfectban.database.repository.BanRepository;
import de.perfectban.meta.Config;
import de.perfectban.meta.Placeholder;
import de.perfectban.util.TimeManager;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.*;

public class PlayerJoinListener implements Listener
{
    private final BanRepository repository;

    public PlayerJoinListener() {
        this.repository = new BanRepository(PerfectBan.getInstance().getEntityManager());
    }

    @EventHandler
    public void onJoin(LoginEvent event) {
        UUID uuid = event.getConnection().getUniqueId();
        Date now = new Date();

        // find ban by searching for UUID
        List<Ban> bans = repository.getBans(uuid);

        // player not banned, return
        if (bans.isEmpty()) {
            return;
        }

        Ban ban = bans.get(0);

        // if not lifetime & until is smaller than current time, mark ban as inactive
        if (!ban.isLifetime() && ban.getUntil().getTime() <= now.getTime()) {
            repository.deleteBan(ban.getId(), null, null);
            return;
        }

        // prepare ban message
        HashMap<Placeholder, Object> replacements = new HashMap<>();
        replacements.put(Placeholder.ID, ban.getId());
        replacements.put(Placeholder.REASON, ban.getReason());
        replacements.put(Placeholder.PLAYER, event.getConnection().getName());
        replacements.put(
            Placeholder.UNTIL,
            ban.getUntil() == null
                ? ConfigManager.getString(ConfigType.MESSAGES, Config.PERMANENT)
                : ban.getUntil().toLocalDateTime().toString()
        );
        replacements.put(
            Placeholder.BANNED_BY,
            ban.getModerator() == null
                ? ConfigManager.getString(ConfigType.MESSAGES, Config.CONSOLE)
                : ban.getModerator()
        );
        replacements.put(
            Placeholder.TIME_LEFT,
            ban.isLifetime()
                ? ConfigManager.getString(ConfigType.MESSAGES, Config.PERMANENT)
                : new TimeManager().convertToTimeString(ban.getUntil().getTime() - System.currentTimeMillis())
        );

        String banMessage = Placeholder.replace(
            ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_MESSAGE),
            replacements
        );

        // disallow player from joining
        event.setCancelled(true);
        event.setCancelReason(new TextComponent(banMessage));
    }
}
