package de.perfectban.command;

import java.util.*;

public class CommandParser
{
    private final HashMap<Character, Long> timeKeys;

    public CommandParser() {
        this.timeKeys = new HashMap<>();

        this.timeKeys.put('s', 1000L);
        this.timeKeys.put('m', 60000L);
        this.timeKeys.put('h', 3600000L);
        this.timeKeys.put('d', 86400000L);
        this.timeKeys.put('w', 604800000L);
        this.timeKeys.put('M', 2629746000L);
        this.timeKeys.put('y', 31556952000L);
    }

    public String getTime(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();

        args = Arrays.copyOfRange(args, 1, args.length);

        for (String argument : reverseArray(args)) {
            Character last = argument.charAt(argument.length() - 1);
            String number = argument.substring(0, argument.length() - 1);

            try {
                int value = Integer.parseInt(number);
            } catch (NumberFormatException e) {
                break;
            }

            if (!timeKeys.containsKey(last)) {
                break;
            }

            stringBuilder.append(stringBuilder.toString().equals("") ? "" : " ").append(argument);
        }

        // no time provided -> default
        if (stringBuilder.toString().isEmpty()) {
            return null;
        }

        return stringBuilder.toString();
    }

    public String getReason(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();

        args = Arrays.copyOfRange(args, 1, args.length);

        for (String argument : args) {
            Character last = argument.charAt(argument.length() - 1);
            String number = argument.substring(0, argument.length() - 1);

            try {
                int value = Integer.parseInt(number);

                if (timeKeys.containsKey(last)) {
                    break;
                }
            } catch (NumberFormatException ignored) {}

            stringBuilder.append(stringBuilder.toString().equals("") ? "" : " ").append(argument);
        }

        return stringBuilder.toString();
    }

    private String[] reverseArray(String[] arr) {
        List<String> list = Arrays.asList(arr);
        Collections.reverse(list);

        return list.toArray(arr);
    }
}
