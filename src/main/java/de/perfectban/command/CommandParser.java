package de.perfectban.command;

import java.util.*;
import java.util.regex.Pattern;

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

    public CommandArguments getArguments(String[] args) {
        StringJoiner timeJoiner = new StringJoiner(" ");
        StringJoiner reasonJoiner = new StringJoiner(" ");

        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        String[] reversed = reverseArray(args);

        for (String argument : reversed) {
            Character last = argument.charAt(argument.length() - 1);
            String number = argument.substring(0, argument.length() - 1);

            if (!pattern.matcher(number).matches() || !timeKeys.containsKey(last)) {
                break;
            }

            timeJoiner.add(argument);
        }

        for (String argument : args) {
            Character last = argument.charAt(argument.length() - 1);
            String number = argument.substring(0, argument.length() - 1);

            if (pattern.matcher(number).matches() && timeKeys.containsKey(last)) {
                break;
            }

            reasonJoiner.add(argument);
        }

        return new CommandArguments(
            reasonJoiner.toString().isEmpty() ? null : reasonJoiner.toString(),
            timeJoiner.toString().isEmpty() ? null : timeJoiner.toString()
        );
    }

    private String[] reverseArray(String[] arr) {
        List<String> list = Arrays.asList(arr);
        Collections.reverse(list);

        return list.toArray(arr);
    }
}
