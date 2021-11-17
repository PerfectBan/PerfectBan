package de.perfectban.bungeecord.command;

import de.perfectban.meta.Placeholder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author Christian Tschörner
 */
public class BungeeCommandSender implements CommandSender {

    public String getName() {
        return null;
    }

    @Override
    public void sendMessage(String message) {
        this.sendMessage(message, new HashMap<>());
    }

    public void sendMessages(String... strings) {

    }

    public void sendMessage(BaseComponent... baseComponents) {

    }

    public void sendMessage(BaseComponent baseComponent) {

    }

    public Collection<String> getGroups() {
        return null;
    }

    public void addGroups(String... strings) {

    }

    public void removeGroups(String... strings) {

    }

    public boolean hasPermission(String s) {
        return false;
    }

    public void setPermission(String s, boolean b) {

    }

    public Collection<String> getPermissions() {
        return null;
    }

    public void sendMessage(String message, HashMap<Placeholder, Object> replacements) {
        this.sendMessage(new TextComponent(Placeholder.replace(message, replacements)));
    }
}
