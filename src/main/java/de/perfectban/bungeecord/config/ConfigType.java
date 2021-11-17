package de.perfectban.bungeecord.config;

public enum ConfigType
{
    MESSAGES("messages.yml", "messages.yml", "messages"),
    CONFIG("config.yml", "config.yml", "config"),
    COMMANDS("commands.yml", "commands.yml", "commands"),
    DATABASE("database.yml", "database.yml", "database");

    private String path, name, defaults;

    ConfigType(String path, String defaults, String name) {
        this.path = path;
        this.defaults = defaults;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public ConfigType setPath(String path) {
        this.path = path;
        return this;
    }

    public String getDefaults() {
        return defaults;
    }

    public ConfigType setDefaults(String defaults) {
        this.defaults = defaults;
        return this;
    }

    public String getName() {
        return name;
    }

    public ConfigType setName(String name) {
        this.name = name;
        return this;
    }
}
