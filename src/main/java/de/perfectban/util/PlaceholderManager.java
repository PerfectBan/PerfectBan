package de.perfectban.util;

import de.perfectban.bungeecord.config.ConfigManager;
import de.perfectban.bungeecord.config.ConfigType;
import de.perfectban.database.entity.Ban;
import de.perfectban.meta.Config;

public class PlaceholderManager {

    public static String replaceBanPlaceholders(String message, Ban ban, String player) {
        TimeManager timeManager = new TimeManager();

        message = replacePrefix(message);
        message = message.replace("{ID}", String.valueOf(ban.getId()));
        message = message.replace("{REASON}", ban.getReason());
        message = message.replace("{BANNED_BY}", ban.getModerator());
        message = message.replace("{PLAYER}", player);
        message = message.replace("{UNTIL}", ban.getUntil() == null
            ? "N/A"
            : ban.getUntil().toLocaleString());
        message = message.replace("{TIME}", ban.isLifetime()
            ? "Forever"
            : timeManager.convertToString(ban.getUntil().getTime() - System.currentTimeMillis()));

        return message;
    }

    public static String replacePrefix(String message) {
        return message.replace("{PREFIX}", ConfigManager.getString(ConfigType.MESSAGES, Config.PREFIX));
    }
}
