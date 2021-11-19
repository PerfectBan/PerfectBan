package de.perfectban.meta;

import de.perfectban.config.ConfigManager;
import de.perfectban.config.ConfigType;

import java.util.HashMap;

public enum Placeholder
{
    PREFIX(ConfigManager.getString(ConfigType.MESSAGES, Config.PREFIX)),
    PLAYER(null),
    ID(null),
    TIME_LEFT(null),
    UNTIL(null),
    BANNED_BY(null),
    REASON(null);

    private final String defaultValue;

    Placeholder(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getTemplate() {
        return String.format("{%s}", this);
    }

    public static String replace(String message, HashMap<Placeholder, Object> replacements) {
        for(Placeholder placeholder : Placeholder.values()){
            String template = placeholder.getTemplate();
            String defaultValue = placeholder.getDefaultValue();

            if (defaultValue != null) {
                message = message.replace(template, defaultValue);
            }

            if (replacements.containsKey(placeholder)) {
                message = message.replace(template, replacements.get(placeholder).toString());
            }
        }

        return message;
    }
}
