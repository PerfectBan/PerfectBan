package de.perfectban.event.bungeecord;

import de.perfectban.PerfectBan;
import de.perfectban.config.ConfigManager;
import de.perfectban.config.ConfigType;
import de.perfectban.entity.Ban;
import de.perfectban.entity.repository.BanRepository;
import de.perfectban.util.PlaceholderManager;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PlayerJoinListener implements Listener {
    private final BanRepository repository;

    public PlayerJoinListener() {
        this.repository = new BanRepository(PerfectBan.getInstance().getEntityManager());
    }

    @EventHandler
    public void onJoin(LoginEvent event) {
        Configuration configuration = ConfigManager.getConfig(ConfigType.MESSAGES);
        assert configuration != null;

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
            repository.deleteBan(ban.getId());
            return;
        }

        String banMessage = PlaceholderManager.replaceBanPlaceholders(
                configuration.getString("perfectban.ban.ban_message"), ban);

        // disallow player from joining
        event.setCancelled(true);
        event.setCancelReason(new TextComponent(banMessage));
    }
}
