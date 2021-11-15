package de.perfectban.config;

import de.perfectban.PerfectBan;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class ConfigManager {

    private static final HashMap<ConfigType, Configuration> configurations = new HashMap<>();

    public static Configuration getConfig(ConfigType configType) {
//        if (configurations.containsKey(configType)) {
//            return configurations.get(configType);
//        }

        try {
            File file = new File(PerfectBan.getInstance().getDataFolder().getPath(), configType.getPath());

            if (!file.exists()) {
                file.createNewFile();

                InputStream inputStream = PerfectBan.getInstance().getResourceAsStream(configType.getDefaults());

                if (inputStream != null) {
                    Files.copy(inputStream, file.toPath());
                }
            }

            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

            configurations.put(configType, configuration);

            return configuration;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Configuration();
    }
}
