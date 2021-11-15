package de.perfectban.config;

import de.perfectban.PerfectBan;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;

public class ConfigManager {

    static {
        PerfectBan.getInstance().getDataFolder().mkdirs();

        //initalize all configs
        try {
            for (ConfigType configType : ConfigType.values()) {
                createConfig(configType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(ConfigType configType, String key){
        return getObject(configType, key).toString();
    }

    public static boolean getBoolean(ConfigType configType, String key){
        return Boolean.parseBoolean(getObject(configType, key).toString());
    }

    public static int getInteger(ConfigType configType, String key){
        return Integer.parseInt(getObject(configType, key).toString());
    }

    private static Object getObject(ConfigType configType, String key) {
        Configuration configuration = getConfig(configType, false);
        Configuration defaultConfiguration = getConfig(configType, true);

        if(configuration != null && !configuration.getString(key).isEmpty()){
            return configuration.get(key);
        }

        if(defaultConfiguration != null && !defaultConfiguration.getString(key).isEmpty()){
            return defaultConfiguration.get(key);
        }

        return key;
    }

    private static void createConfig(ConfigType configType) throws IOException {
        File configFile = new File(PerfectBan.getInstance().getDataFolder().getPath(), configType.getPath());
        InputStream defaultInputStream = PerfectBan.getInstance().getResourceAsStream(configType.getDefaults());

        if(configFile.exists() || defaultInputStream == null){
            return;
        }

        Files.copy(defaultInputStream, configFile.toPath());
    }

    private static Configuration getConfig(ConfigType configType, boolean useDefault) {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(getInputStream(configType, useDefault));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static InputStream getInputStream(ConfigType configType, boolean useDefault) throws FileNotFoundException {
        if(useDefault){
            return PerfectBan.getInstance().getResourceAsStream(configType.getDefaults());
        }else{
            return new FileInputStream(
                    new File(PerfectBan.getInstance().getDataFolder().getPath(), configType.getPath()));
        }
    }
}
