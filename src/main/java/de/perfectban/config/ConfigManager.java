package de.perfectban.config;

import de.perfectban.PerfectBan;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager {

    public static Configuration getConfig(ConfigType configType) {
        try {
            File file = new File(PerfectBan.getInstance().getDataFolder().getPath(), configType.getPath());

            if (!file.exists()) {
                file.createNewFile();

                InputStream inputStream = PerfectBan.getInstance().getResourceAsStream(configType.getDefaults());

                if (inputStream != null) {
                    Files.copy(inputStream, file.toPath());
                }
            }

            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Configuration();
    }
}
