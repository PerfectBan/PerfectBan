package de.perfectban;

import de.perfectban.entity.Ban;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class PerfectBan extends Plugin {

    public static final String BASE_PACKAGE = "de.perfectban";

    private static PerfectBan instance;
    private EntityManager entityManager;

    @Override
    public void onEnable() {
        instance = this;

        // setup hibernate
        this.entityManager = Persistence.createEntityManagerFactory(BASE_PACKAGE).createEntityManager();

        // todo: remove
        Ban ban = PerfectBan.getInstance().getEntityManager().find(Ban.class, 1);

        if (ban == null) {
            ProxyServer.getInstance().broadcast(new TextComponent("§cBan not found"));
            return;
        }

        ProxyServer.getInstance().broadcast(new TextComponent(ban.getReason()));
    }

    @Override
    public void onDisable() {
        
    }

    public static PerfectBan getInstance() {
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
