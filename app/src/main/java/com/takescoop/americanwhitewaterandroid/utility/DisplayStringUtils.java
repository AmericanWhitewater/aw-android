package com.takescoop.americanwhitewaterandroid.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
        return formatter.format(instant).toLowerCase(Locale.US);
    }

    public static String getVersionString(Context context) {
        String versionName = DisplayStringUtils.getVersionName(context);
        String versionCode = DisplayStringUtils.getVersionCode(context);
        return versionName + "." + versionCode;
    }

    private static String getVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    private static String getVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return String.valueOf(info.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }
}
