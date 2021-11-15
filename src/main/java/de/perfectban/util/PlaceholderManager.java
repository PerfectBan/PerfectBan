package de.perfectban.util;

import de.perfectban.config.ConfigManager;
import de.perfectban.config.ConfigType;
import de.perfectban.entity.Ban;
import de.perfectban.meta.Config;

public class PlaceholderManager {

    public static String replaceBanPlaceholders(String message, Ban ban) {
        message = message.replace("{PREFIX}", ConfigManager.getString(ConfigType.MESSAGES, Config.PREFIX));
        message = message.replace("{ID}", String.valueOf(ban.getId()));
        message = message.replace("{REASON}", ban.getReason());
        message = message.replace("{TIME}", ban.isLifetime()
            ? "Forever"
            : TimeManager.convertToString(ban.getUntil().getTime() - System.currentTimeMillis()));

        return message;
    }
}
