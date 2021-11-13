package de.perfectban;

import net.md_5.bungee.api.plugin.Plugin;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class PerfectBan extends Plugin {

    public static final String PACKAGE = "de.perfectban";

    private PerfectBan instance;

    private EntityManager entityManager;

    @Override
    public void onEnable() {
        super.onEnable();

        this.instance = this;

        // setup hibernate
        this.entityManager = Persistence.createEntityManagerFactory(PACKAGE).createEntityManager();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public PerfectBan getInstance() {
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
