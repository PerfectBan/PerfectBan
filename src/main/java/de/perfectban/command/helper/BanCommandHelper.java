package de.perfectban.command.helper;

import de.perfectban.PerfectBan;
import de.perfectban.config.ConfigManager;
import de.perfectban.config.ConfigType;
import de.perfectban.database.entity.Ban;
import de.perfectban.database.repository.BanRepository;
import de.perfectban.meta.Config;
import de.perfectban.meta.Placeholder;
import de.perfectban.util.TimeManager;
import de.perfectban.util.UUIDFetcher;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class BanCommandHelper
{
    private final BanRepository repository;
    private final TimeManager timeManager;

    public BanCommandHelper() {
        this.repository = new BanRepository(PerfectBan.getInstance().getEntityManager());
        this.timeManager = new TimeManager();
    }

    public void ban(String player, String reason, String time, boolean permanent, UUID moderator, Consumer<String> callback) {
        UUIDFetcher.getUUIDbyName(player, uuid -> {
            if (uuid == null) {
                callback.accept(Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_PLAYER_NOT_FOUND),
                    new HashMap<>()
                ));
                return;
            }

            // check if reason is set
            if (reason == null || reason.isEmpty()) {
                callback.accept(Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_REASON),
                    new HashMap<>()
                ));
                return;
            }

            List<Ban> bans = repository.getBans(uuid);

            if (!bans.isEmpty()) {
                callback.accept(Placeholder.replace(
                        ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_BANNED),
                        new HashMap<>()
                ));
                return;
            }

            long diff = 0;
            Timestamp until = new Timestamp(System.currentTimeMillis());

            // check if diff is set
            if (time != null && !time.isEmpty()) {
                diff = timeManager.convertToMillis(time);
                until = new Timestamp(System.currentTimeMillis() + diff);
            }

            // ban player
            Ban ban = repository.createBan(uuid, reason, until, permanent, false, moderator);

            HashMap<Placeholder, Object> replacements = getBanReplacements(player, ban);

            if (diff > 0) {
                replacements.put(Placeholder.TIME_LEFT, timeManager.convertToTimeString(diff));
            }

            if (ConfigManager.getBoolean(ConfigType.CONFIG, Config.USE_BROADCAST)) {
                String message = Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BROADCAST_CREATE),
                    replacements
                );
                // todo: broadcast
            }

            callback.accept(Placeholder.replace(
                ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BAN_CREATE),
                replacements
            ));

            // check if player is online
            ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(player);

            if (proxiedPlayer == null || !proxiedPlayer.isConnected()) {
                return;
            }

            String banMessage = Placeholder.replace(
                ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_MESSAGE),
                replacements
            );

            // kick player from server
            proxiedPlayer.disconnect(new TextComponent(banMessage));
        });
    }

    public void deleteBan(String player, String reason, UUID moderator, Consumer<String> callback) {
        UUIDFetcher.getUUIDbyName(player, uuid -> {
            if (uuid == null) {
                callback.accept(Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_PLAYER_NOT_FOUND),
                    new HashMap<>()
                ));
                return;
            }

            List<Ban> bans = repository.getBans(uuid);

            if (bans.isEmpty()) {
                callback.accept(Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_NOT_BANNED),
                    new HashMap<>()
                ));
                return;
            }

            Ban ban = bans.get(0);

            // soft delete ban
            repository.deleteBan(ban.getId(), moderator, reason);

            // broadcast to moderators
            HashMap<Placeholder, Object> replacements = getBanReplacements(player, ban);

            if (ConfigManager.getBoolean(ConfigType.CONFIG, Config.USE_BROADCAST)) {
                String message = Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BROADCAST_DELETE),
                    replacements
                );
                // todo: broadcast
            }

            callback.accept(Placeholder.replace(
                ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BAN_DELETE),
                replacements
            ));
        });
    }

    public void changeBan(String player, String reason, String time, boolean permanent, UUID moderator, Consumer<String> callback) {
        UUIDFetcher.getUUIDbyName(player, uuid -> {
            if (uuid == null) {
                callback.accept(Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_PLAYER_NOT_FOUND),
                    new HashMap<>()
                ));
                return;
            }

            List<Ban> bans = repository.getBans(uuid);

            if (bans.isEmpty()) {
                callback.accept(Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_NOT_BANNED),
                    new HashMap<>()
                ));
                return;
            }

            Ban ban = bans.get(0);

            long diff = 0;
            Timestamp until = new Timestamp(System.currentTimeMillis());

            // check if diff is set
            if (time != null && !time.isEmpty()) {
                diff = timeManager.convertToMillis(time);
                until = new Timestamp(System.currentTimeMillis() + diff);
            }

            repository.editBan(ban.getId(), reason, until, permanent, moderator);

            HashMap<Placeholder, Object> replacements = getBanReplacements(player, ban);

            if (diff > 0) {
                replacements.put(Placeholder.TIME_LEFT, timeManager.convertToTimeString(diff));
            }

            // broadcast to moderators
            if (ConfigManager.getBoolean(ConfigType.CONFIG, Config.USE_BROADCAST)) {
                String message = Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BROADCAST_CHANGE),
                    replacements
                );

                // todo: broadcast
            }

            callback.accept(Placeholder.replace(
                ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BAN_CHANGE),
                replacements
            ));
        });
    }

    public void banInfo(String player, Consumer<String> callback) {
        UUIDFetcher.getUUIDbyName(player, uuid -> {
            if (uuid == null) {
                callback.accept(Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_PLAYER_NOT_FOUND),
                    new HashMap<>()
                ));
                return;
            }

            List<Ban> bans = repository.getBans(uuid);

            // todo: history
            if (bans.isEmpty()) {
                callback.accept(Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_NOT_BANNED),
                    new HashMap<>()
                ));
                return;
            }

            callback.accept(Placeholder.replace(
                ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BAN_INFO),
                getBanReplacements(player, bans.get(0))
            ));
        });
    }

    private HashMap<Placeholder, Object> getBanReplacements(String player, Ban ban) {
        HashMap<Placeholder, Object> replacements = new HashMap<>();
        replacements.put(Placeholder.ID, ban.getId());
        replacements.put(Placeholder.REASON, ban.getReason());
        replacements.put(Placeholder.PLAYER, player);
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

        return replacements;
    }
}
