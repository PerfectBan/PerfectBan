package de.perfectban;

import de.perfectban.command.ban.BanCommand;
import de.perfectban.config.ConfigManager;
import de.perfectban.config.ConfigType;
import de.perfectban.entity.Ban;
import de.perfectban.entity.Blocklist;
import de.perfectban.entity.Mute;
import de.perfectban.event.bungeecord.PlayerJoinListener;
import de.perfectban.meta.Config;
import net.md_5.bungee.api.plugin.Plugin;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;

public class PerfectBan extends Plugin
{
    public static final String BASE_PACKAGE = "de.perfectban";

    private static PerfectBan instance;

    private EntityManager entityManager;

    @Override
    public void onEnable() {
        instance = this;

        // setup hibernate
        String databaseHost = String.format(
            "jdbc:mysql://%s:%d/%s",
            ConfigManager.getString(ConfigType.DATABASE, Config.DATABASE_HOST),
            ConfigManager.getInteger(ConfigType.DATABASE, Config.DATABASE_PORT),
            ConfigManager.getString(ConfigType.DATABASE, Config.DATABASE_DATABASE)
        );

        Configuration configuration = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect")
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url", databaseHost)
                .setProperty("hibernate.connection.username", ConfigManager.getString(ConfigType.DATABASE, Config.DATABASE_USERNAME))
                .setProperty("hibernate.connection.password", ConfigManager.getString(ConfigType.DATABASE, Config.DATABASE_PASSWORD))
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .addAnnotatedClass(Ban.class)
                .addAnnotatedClass(Mute.class)
                .addAnnotatedClass(Blocklist.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        entityManager = sessionFactory.createEntityManager();

        // setup commands
        this.getProxy().getPluginManager().registerCommand(this, new BanCommand(
            ConfigManager.getString(ConfigType.COMMANDS, Config.COMMAND_BAN_COMMAND),
            ConfigManager.getString(ConfigType.COMMANDS, Config.COMMAND_BAN_PERMISSION)
        ));

        // setup listeners
        this.getProxy().getPluginManager().registerListener(this, new PlayerJoinListener());
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
