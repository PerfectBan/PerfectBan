package de.perfectban.event.bungeecord;

import de.perfectban.PerfectBan;
import de.perfectban.entity.Ban;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void onJoin(LoginEvent event) {
        UUID uuid = event.getConnection().getUniqueId();
        Date now = new Date();

        // find ban by searching for UUID
        List<Ban> bans = PerfectBan
            .getInstance()
            .getEntityManager()
            .createQuery("FROM Ban WHERE uuid = :uuid AND active = :active ORDER BY until DESC", Ban.class)
            .setParameter("uuid", uuid.toString())
            .setParameter("active", true)
            .getResultList();

        ProxyServer.getInstance().broadcast(
                new TextComponent("Es gibt " + bans.size() + " Einträge für " + uuid.toString())
        );

        // player not banned, return
        if (bans.isEmpty()) {
            return;
        }

        Ban ban = bans.get(0);

        // if not lifetime & until is smaller than current time, mark ban as inactive
        if (!ban.isLifetime() && ban.getUntil().getTime() <= now.getTime()) {
            EntityTransaction transaction = PerfectBan.getInstance().getEntityManager().getTransaction();

            // update ban in database
            transaction.begin();
            ban.setActive(false);
            transaction.commit();
            return;
        }

        // disallow player from joining
        event.setCancelled(true);
        event.setCancelReason(new TextComponent(
            String.format("§cYou are banned!\n\n§f#%s\n\n§eGrund: §f%s\n§eUntil: §f%s",
                ban.getId(),
                ban.getReason(),
                ban.isLifetime() ? "Lifetime" : ban.getUntil().toString()
            )
        ));
    }
}
