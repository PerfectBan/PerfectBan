package de.perfectban.util;

import de.perfectban.entity.Ban;

public class PlaceholderManager {

    public static String replaceBanPlaceholders(String message, Ban ban) {
        message = message.replace("%ID%", String.valueOf(ban.getId()));
        message = message.replace("%REASON%", ban.getReason());
        message = message.replace("%TIME%", ban.isLifetime()
            ? "Forever"
            : TimeManager.convertToString(ban.getUntil().getTime() - System.currentTimeMillis()));

        return message;
    }
}
