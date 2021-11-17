package de.perfectban.command.helper;

import de.perfectban.PerfectBan;
import de.perfectban.bungeecord.config.ConfigManager;
import de.perfectban.bungeecord.config.ConfigType;
import de.perfectban.database.entity.Ban;
import de.perfectban.database.repository.BanRepository;
import de.perfectban.meta.Config;
import de.perfectban.meta.Placeholder;
import de.perfectban.util.TimeManager;
import de.perfectban.util.UUIDFetcher;

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
                callback.accept(Placeholder.replace(
                        ConfigManager.getString(ConfigType.MESSAGES, Config.ERROR_BAN_PLAYER_BANNED),
                        new HashMap<>())
                );

                return;
            }

            // ban player
            Ban ban = repository.createBan(uuid, reason, until, permanent, false, moderator);

            String message = Placeholder.replace(ConfigManager.getString(ConfigType.MESSAGES, "banned"),
                    getBanReplacements(player, ban));

            callback.accept(message);
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
            HashMap<Placeholder, Object> replacements = getBanReplacements(player, ban);

            if (ConfigManager.getBoolean(ConfigType.CONFIG, "useBroadcast")) {
                String message = Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BROADCAST_DELETE),
                    replacements
                );
                // todo: broadcast
            }

            String message = Placeholder.replace(ConfigManager.getString(ConfigType.MESSAGES, "deleted"),
                    replacements);
            callback.accept(message);
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

            HashMap<Placeholder, Object> replacements = getBanReplacements(player, ban);

            // broadcast to moderators
            if (ConfigManager.getBoolean(ConfigType.CONFIG, "useBroadcast")) {
                String message = Placeholder.replace(
                        ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_BROADCAST_DELETE),
                        replacements
                );

                // todo: broadcast
            }

            String message = Placeholder.replace(ConfigManager.getString(ConfigType.MESSAGES, "changed"),
                    replacements);
            callback.accept(message);
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

            String message = Placeholder.replace(
                    ConfigManager.getString(ConfigType.MESSAGES, Config.BAN_COMMAND_TEMPLATE_INFO),
                    getBanReplacements(player, bans.get(0)));

            callback.accept(message);
        });
    }

    private HashMap<Placeholder, Object> getBanReplacements(String player, Ban ban){
        //prepare ban message
        HashMap<Placeholder, Object> replacements = new HashMap<>();
        replacements.put(Placeholder.ID, ban.getId());
        replacements.put(Placeholder.REASON, ban.getReason());
        replacements.put(Placeholder.BANNED_BY, ban.getModerator() == null ? "console " : ban.getModerator());
        replacements.put(Placeholder.PLAYER, player);
        replacements.put(Placeholder.UNTIL, ban.getUntil() == null
                ? "N/A"
                : ban.getUntil().toLocaleString());
        replacements.put(Placeholder.TIME_LEFT, ban.isLifetime()
                ? "Forever"
                : new TimeManager().convertToString(ban.getUntil().getTime() - System.currentTimeMillis()));

        return replacements;
    }
}
