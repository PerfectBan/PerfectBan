package de.perfectban.bungeecord.event;

import de.perfectban.PerfectBan;
import de.perfectban.bungeecord.config.ConfigManager;
import de.perfectban.bungeecord.config.ConfigType;
import de.perfectban.database.entity.Ban;
import de.perfectban.database.repository.BanRepository;
import de.perfectban.meta.Config;
import de.perfectban.util.PlaceholderManager;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
            repository.deleteBan(ban.getId(), null);
            return;
        }

        String banMessage = PlaceholderManager.replaceBanPlaceholders(
            ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_MESSAGE), ban, event.getConnection().getName()
        );

        // disallow player from joining
        event.setCancelled(true);
        event.setCancelReason(new TextComponent(banMessage));
    }
}
