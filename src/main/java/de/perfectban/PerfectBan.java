package de.perfectban;

import de.perfectban.config.ConfigManager;
import de.perfectban.config.ConfigType;
import de.perfectban.entity.Ban;
import de.perfectban.entity.Blocklist;
import de.perfectban.entity.Mute;
import net.md_5.bungee.api.plugin.Plugin;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;

public class PerfectBan extends Plugin {

    public static final String BASE_PACKAGE = "de.perfectban";

    private static PerfectBan instance;

    private EntityManager entityManager;
    private SessionFactory sessionFactory;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;

        // setup hibernate
        // todo: use configuration to set those values and catch any errors (display them user friendly)
        Configuration configuration = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://database.tschoerner.cloud:3306/perfectban")
                .setProperty("hibernate.connection.username", "perfectban")
                .setProperty("hibernate.connection.password", "NX093ZCCbO6qAwbZ")
                .addAnnotatedClass(Ban.class)
                .addAnnotatedClass(Mute.class)
                .addAnnotatedClass(Blocklist.class);

        sessionFactory = configuration.buildSessionFactory();
        entityManager = sessionFactory.createEntityManager();

        // setup configs
        configManager = new ConfigManager();

        // setup commands

        // setup listeners
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

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
