package de.perfectban.config;

import de.perfectban.PerfectBan;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;

public class Config
{
    private Configuration configuration;
    private ConfigType configType;

    public Config(ConfigType configType) {
        try {
            File file = new File(PerfectBan.getInstance().getDataFolder().getPath(), configType.getPath());

            if (!file.exists()) {
                    InputStream in = PerfectBan.getInstance().getResourceAsStream(configType.getPath());
                    Files.copy(in, file.toPath());
            }

            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.configType = configType;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Config setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public ConfigType getConfigType() {
        return configType;
    }

    public Config setConfigType(ConfigType configType) {
        this.configType = configType;
        return this;
    }
}
