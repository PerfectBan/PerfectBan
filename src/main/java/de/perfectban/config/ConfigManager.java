package de.perfectban.config;

import java.util.HashMap;

public class ConfigManager {

    private final HashMap<ConfigType, Config> configs = new HashMap<>();

    public ConfigManager() {
        reloadConfigs();
    }

    public Config getConfig(ConfigType configType) {
        return configs.get(configType);
    }

    public void reloadConfig(ConfigType configType) {
        configs.put(configType, new Config(configType));
    }

    public void reloadConfigs() {
        for (ConfigType configType : ConfigType.values()) {
            configs.put(configType, new Config(configType));
        }
    }
}
