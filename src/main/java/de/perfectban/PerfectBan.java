package de.perfectban;

import de.perfectban.bungeecord.command.ban.BanCommand;
import de.perfectban.bungeecord.command.ban.ChangeBanCommand;
import de.perfectban.bungeecord.command.ban.UnbanCommand;
import de.perfectban.bungeecord.config.ConfigManager;
import de.perfectban.bungeecord.config.ConfigType;
import de.perfectban.database.entity.Ban;
import de.perfectban.database.entity.BanChange;
import de.perfectban.database.entity.Blocklist;
import de.perfectban.database.entity.Mute;
import de.perfectban.bungeecord.listener.PlayerJoinListener;
import de.perfectban.meta.Config;
import net.md_5.bungee.api.plugin.Plugin;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;

public class PerfectBan extends Plugin
{
    private static PerfectBan instance;

    private EntityManager entityManager;
    private SessionFactory sessionFactory;

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
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect") // todo: add these back
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url", databaseHost)
                .setProperty("hibernate.connection.username", ConfigManager.getString(ConfigType.DATABASE, Config.DATABASE_USERNAME))
                .setProperty("hibernate.connection.password", ConfigManager.getString(ConfigType.DATABASE, Config.DATABASE_PASSWORD))
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .addAnnotatedClass(Ban.class)
                .addAnnotatedClass(BanChange.class)
                .addAnnotatedClass(Mute.class)
                .addAnnotatedClass(Blocklist.class);

        sessionFactory = configuration.buildSessionFactory();
        entityManager = sessionFactory.createEntityManager();

        // bungeecord start

        // setup commands
        this.getProxy().getPluginManager().registerCommand(this, new BanCommand(
            ConfigManager.getString(ConfigType.COMMANDS, Config.COMMAND_BAN_COMMAND),
            ConfigManager.getString(ConfigType.COMMANDS, Config.COMMAND_BAN_PERMISSION)
        ));
        this.getProxy().getPluginManager().registerCommand(this, new UnbanCommand(
            ConfigManager.getString(ConfigType.COMMANDS, Config.COMMAND_UNBAN_COMMAND),
            ConfigManager.getString(ConfigType.COMMANDS, Config.COMMAND_UNBAN_PERMISSION)
        ));
        this.getProxy().getPluginManager().registerCommand(this, new ChangeBanCommand(
            ConfigManager.getString(ConfigType.COMMANDS, Config.COMMAND_CHANGE_BAN_COMMAND),
            ConfigManager.getString(ConfigType.COMMANDS, Config.COMMAND_CHANGE_BAN_PERMISSION)
        ));

        // setup listeners
        this.getProxy().getPluginManager().registerListener(this, new PlayerJoinListener());


        // bukkit start

        // todo
    }

    @Override
    public void onDisable() {
        // todo
    }

    public static PerfectBan getInstance() {
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
