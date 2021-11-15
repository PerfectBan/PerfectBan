package de.perfectban.config;

public enum ConfigType {

    MESSAGES("messages.yml", "resources/config/messages.yml", "messages"),
    SETTINGS("settings.yml", "resources/config/settings.yml", "settings"),
    PERMISSIONS("permissions.yml", "resources/config/permissions.yml", "permissions");

    private String path, name;

    ConfigType(String path, String defaults, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public ConfigType setPath(String path) {
        this.path = path;
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
