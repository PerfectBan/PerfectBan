package de.perfectban;

import de.perfectban.command.CommandListener;
import de.perfectban.command.CommandManager;
import de.perfectban.command.ban.BanCommand;
import de.perfectban.entity.Ban;
import de.perfectban.event.bungeecord.PlayerJoinListener;
import net.md_5.bungee.api.plugin.Plugin;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;

public class PerfectBan extends Plugin {

    public static final String BASE_PACKAGE = "de.perfectban";

    private static PerfectBan instance;

    private CommandManager commandManager;
    private EntityManager entityManager;
    private SessionFactory sessionFactory;

    @Override
    public void onEnable() {
        instance = this;

        // setup hibernate
        // todo: use configuration to set those values and catch any errors (display them user friendly)
        Configuration configuration = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://host:port/database")
                .setProperty("hibernate.connection.username", "username")
                .setProperty("hibernate.connection.password", "password")
                .addAnnotatedClass(Ban.class);

        sessionFactory = configuration.buildSessionFactory();
        entityManager = sessionFactory.createEntityManager();

        // setup commands
        commandManager = new CommandManager();
        commandManager.addCommand(new BanCommand());

        // setup listeners
        this.getProxy().getPluginManager().registerListener(this, new PlayerJoinListener());
        this.getProxy().getPluginManager().registerListener(this, new CommandListener());
    }

    @Override
    public void onDisable() {
        
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static PerfectBan getInstance() {
        return instance;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
