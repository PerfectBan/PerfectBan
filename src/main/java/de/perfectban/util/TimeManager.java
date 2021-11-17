package de.perfectban.util;

import java.util.*;

public class TimeManager
{
    private final LinkedHashMap<Character, Long> timeKeys;
    private final LinkedHashMap<String, Long> cache;

    public TimeManager() {
        this.cache = new LinkedHashMap<>();
        this.timeKeys = new LinkedHashMap<>();

        this.timeKeys.put('y', 31556952000L);
        this.timeKeys.put('M', 2629746000L);
        this.timeKeys.put('w', 604800000L);
        this.timeKeys.put('d', 86400000L);
        this.timeKeys.put('h', 3600000L);
        this.timeKeys.put('m', 60000L);
        this.timeKeys.put('s', 1000L);
    }

    public long convertToMillis(String time) {
        if (time == null) {
            return 0;
        }

        if (cache.containsKey(time)) {
            return cache.get(time);
        }

        String[] split = time.split("/\\s/");
        long millis = 0L;

        for (String value : split) {
            int number = Integer.parseInt(value.substring(0, value.length() - 1));
            Character last = value.charAt(value.length() - 1);

            if (timeKeys.containsKey(last)) {
                millis += timeKeys.get(last) * number;
            }
        }

        cache.put(time, millis);

        return millis;
    }

    public String convertToTimeString(long diff) {
        LinkedHashMap<Character, Integer> values = new LinkedHashMap<>();

        for(Map.Entry<Character, Long> entry : timeKeys.entrySet()) {
            Character key = entry.getKey();
            Long value = entry.getValue();

            int counter = 0;

            while (diff >= value) {
                diff -= value;
                counter ++;
            }

            values.put(key, counter);
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(Map.Entry<Character, Integer> entry : values.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();

            if (value > 0) {
                stringBuilder
                    .append(stringBuilder.toString().isEmpty() ? "" : " ")
                    .append(value).append(key);
            }
        }

        return stringBuilder.toString();
    }
}
