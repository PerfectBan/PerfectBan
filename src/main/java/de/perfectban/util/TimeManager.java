package de.perfectban.util;

public class TimeManager {

    public static long convertToMillis(String time) {
        if (time == null) {
            return 0;
        }

        String[] split = time.split("/\\s/");

        long millis = 0L;

        for (String value : split) {
            int number = Integer.parseInt(value.substring(0, value.length() - 1));

            if (value.endsWith("s")) {
                millis += 1000L * number;
            } else if (value.endsWith("m")) {
                millis += 60000L * number;
            } else if (value.endsWith("h")) {
                millis += 3600000L * number;
            } else if (value.endsWith("d")) {
                millis += 86400000L * number;
            } else if (value.endsWith("w")) {
                millis += 604800000L * number;
            }
        }

        return millis;
    }

    public static String convertToString(long diff) {
        int days = 0, hours = 0, minutes = 0, seconds = 0;

        while (diff >= 86400000L) {
            diff -= 86400000L;
            days ++;
        }

        while (diff >= 3600000L) {
            diff -= 3600000L;
            hours ++;
        }

        while (diff >= 60000L) {
            diff -= 60000L;
            minutes ++;
        }

        while (diff >= 1000L) {
            diff -= 1000L;
            seconds ++;
        }

        if (days == 0 && hours == 0) {
            return String.format(
                    "%s minute%s, %s second%s",
                    minutes, (minutes != 1 ? "s" : ""),
                    seconds, (seconds != 1 ? "s" : "")
            );
        }

        if (days == 0) {
            return String.format(
                "%s hour%s, %s minute%s",
                hours, (hours != 1 ? "s" : ""),
                minutes, (minutes != 1 ? "s" : "")
            );
        }

        return String.format(
            "%s day%s, %s hour%s, %s minute%s",
            days, (days != 1 ? "s" : ""),
            hours, (hours != 1 ? "s" : ""),
            minutes, (minutes != 1 ? "s" : "")
        );
    }
}
