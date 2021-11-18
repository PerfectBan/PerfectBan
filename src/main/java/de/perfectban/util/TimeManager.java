package de.perfectban.util;

import java.util.*;

public class TimeManager
{
    private final LinkedHashMap<Character, Long> timeKeys;

    public TimeManager() {
        this.timeKeys = new LinkedHashMap<>();

        this.timeKeys.put('y', 31556952000L);
        this.timeKeys.put('M', 2629746000L);
        this.timeKeys.put('d', 86400000L);
        this.timeKeys.put('h', 3600000L);
        this.timeKeys.put('m', 60000L);
        this.timeKeys.put('s', 1000L);
    }

    public long convertToMillis(String time) {
        if (time == null) {
            return 0;
        }

        String[] split = time.split(" ");
        long millis = 0L;

        for (String argument : split) {
            Character key = argument.charAt(argument.length() - 1);
            Long value = Long.parseLong(argument.substring(0, argument.length() - 1));

            if (timeKeys.containsKey(key)) {
                millis += timeKeys.get(key) * value;
            }
        }

        return millis;
    }

    public String convertToTimeString(long diff) {
        LinkedHashMap<Character, Integer> counts = new LinkedHashMap<>();

        for(Map.Entry<Character, Long> entry : timeKeys.entrySet()) {
            Character key = entry.getKey();
            Long value = entry.getValue();

            int counter = 0;

            while (diff >= value) {
                diff -= value;
                counter ++;
            }

            counts.put(key, counter);
        }

        StringJoiner joiner = new StringJoiner(" ");

        for(Map.Entry<Character, Integer> entry : counts.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();

            if (value > 0) {
                joiner.add(String.format("%s%s", value, key));
            }
        }

        return joiner.toString();
    }
}
