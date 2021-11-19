package de.perfectban.command;

public enum CommandAction
{
    CREATE(),
    READ(),
    UPDATE(),
    DELETE();

    CommandAction() {}

    public String getMethod() {
        return this.toString().toLowerCase();
    }
}
