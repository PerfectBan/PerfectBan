package de.perfectban.command;

import java.util.UUID;

public class CommandArguments
{
    private String player, reason, time;
    private boolean permanent;
    private UUID moderator;

    public CommandArguments() {}

    public String getPlayer() {
        return player;
    }

    public CommandArguments setPlayer(String player) {
        this.player = player;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public CommandArguments setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getTime() {
        return time;
    }

    public CommandArguments setTime(String time) {
        this.time = time;
        return this;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public CommandArguments setPermanent(boolean permanent) {
        this.permanent = permanent;
        return this;
    }

    public UUID getModerator() {
        return moderator;
    }

    public CommandArguments setModerator(UUID moderator) {
        this.moderator = moderator;
        return this;
    }
}
