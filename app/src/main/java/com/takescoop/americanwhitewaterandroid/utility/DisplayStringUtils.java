package com.takescoop.americanwhitewaterandroid.utility;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;
import java.util.TimeZone;

public class DisplayStringUtils {

    public static String displayUpdateTime(Instant lastUpdated) {
        LocalDate localDate = LocalDateTime.ofInstant(lastUpdated, ZoneId.systemDefault()).toLocalDate();
        boolean isToday = localDate.isEqual(LocalDate.now());
        boolean isYesterday = localDate.isEqual(LocalDate.now().minusDays(1));

        String dayString;
        if (isToday) {
            dayString = "Today";
        } else if (isYesterday) {
            dayString = "Yesterday";
        } else {
            dayString = localDate.toString();
        }

        return dayString + " at " + displayTimeHHMMA(lastUpdated);
    }

    public static String displayTimeHHMMA(Instant instant) {
        String pattern = "h:mma";
        if (instant == null) {
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.of("America/Los_Angeles"));
        return formatter.format(instant).toLowerCase(Locale.US);
    }
}
