package de.perfectban.command;

public class CommandArguments
{
    private String reason, time;

    public CommandArguments(String reason, String time) {
        this.reason = reason;
        this.time = time;
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
}
