package de.perfectban.command.helper;

import de.perfectban.PerfectBan;
import de.perfectban.bungeecord.config.ConfigManager;
import de.perfectban.bungeecord.config.ConfigType;
import de.perfectban.database.entity.Ban;
import de.perfectban.database.repository.BanRepository;
import de.perfectban.meta.Config;
import de.perfectban.util.PlaceholderManager;
import de.perfectban.util.TimeManager;
import de.perfectban.util.UUIDFetcher;

import java.sql.Timestamp;
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
                callback.accept(ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_PLAYER_NOT_FOUND));
                return;
            }

            // check if reason and time is set
            if (reason == null || (time == null && !permanent)) {
                callback.accept(ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_TIME));
                return;
            }

            // calculate until
            long diff = timeManager.convertToMillis(time);
            Timestamp until = new Timestamp(System.currentTimeMillis() + diff);

            List<Ban> bans = repository.getBans(uuid);

            if (!bans.isEmpty()) {
                callback.accept(PlaceholderManager.replacePrefix(
                        ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_BANNED)
                ));
                return;
            }

            // ban player
            Ban ban = repository.createBan(uuid, reason, until, permanent, false, moderator);

            callback.accept(PlaceholderManager.replaceBanPlaceholders(ConfigManager.getString(ConfigType.MESSAGES, "banned"), ban, player));
        });
    }

    public void deleteBan(String player, String reason, UUID moderator, Consumer<String> callback) {
        UUIDFetcher.getUUIDbyName(player, uuid -> {
            if (uuid == null) {
                callback.accept(ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_PLAYER_NOT_FOUND));
                return;
            }

            List<Ban> bans = repository.getBans(uuid);

            if (bans.isEmpty()) {
                callback.accept(ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_NOT_BANNED));
                return;
            }

            Ban ban = bans.get(0);

            // soft delete ban
            repository.deleteBan(ban.getId(), moderator);

            // broadcast to moderators
            if (ConfigManager.getBoolean(ConfigType.CONFIG, "useBroadcast")) {
                String message = PlaceholderManager.replaceBanPlaceholders(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BROADCAST_DELETE), ban, player
                );
                // todo: broadcast
            }

            callback.accept(PlaceholderManager.replaceBanPlaceholders(
                ConfigManager.getString(ConfigType.MESSAGES, "deleted"),
                bans.get(0), player
            ));
        });
    }

    public void changeBan(String player, String reason, String time, boolean permanent, UUID moderator, Consumer<String> callback) {
        UUIDFetcher.getUUIDbyName(player, uuid -> {
            if (uuid == null) {
                callback.accept(ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_PLAYER_NOT_FOUND));
                return;
            }

            List<Ban> bans = repository.getBans(uuid);

            if (bans.isEmpty()) {
                callback.accept(ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_NOT_BANNED));
                return;
            }

            Ban ban = bans.get(0);

            // calculate until
            long diff = timeManager.convertToMillis(time);

            if (diff != 0) {
                Timestamp until = new Timestamp(System.currentTimeMillis() + diff);

                repository.editBan(ban.getId(), reason, until, permanent, moderator);
            } else {
                repository.editBan(ban.getId(), reason, null, permanent, moderator);
            }

            // broadcast to moderators
            if (ConfigManager.getBoolean(ConfigType.CONFIG, "useBroadcast")) {
                String message = PlaceholderManager.replaceBanPlaceholders(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BROADCAST_DELETE), ban, player
                );
                // todo: broadcast
            }

            callback.accept(PlaceholderManager.replaceBanPlaceholders(
                ConfigManager.getString(ConfigType.MESSAGES, "changed"),
                bans.get(0), player
            ));
        });
    }

    public void banInfo(String player, Consumer<String> callback) {
        UUIDFetcher.getUUIDbyName(player, uuid -> {
            if (uuid == null) {
                callback.accept(ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_PLAYER_NOT_FOUND));
                return;
            }

            List<Ban> bans = repository.getBans(uuid);

            // todo: history
            if (bans.isEmpty()) {
                callback.accept(ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_NOT_BANNED));
                return;
            }

            callback.accept(ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_TEMPLATE_INFO));
        });
    }
}
