package com.gerosprime.gylog.base.utils;

import java.util.Locale;

public class TimeFormatUtil {


    public static boolean validateTimerFormat(String format) {

        if (format == null || format.isEmpty())
            return false;

        String[] nums = format.split(":");

        if (nums.length != 2)
            return false;

        int mins = Integer.valueOf(nums[0]);
        int seconds = Integer.valueOf(nums[1]);

        if (mins > 60) return false;

        if (seconds > 59) return false;

        return true;

    }


    public static String secondsToString(long seconds) {

        if (seconds < 0)
            throw new IllegalArgumentException("Must be seconds >= 0");

        Long min = seconds / 60;
        Long sec = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", min, sec);
    }


    public static int stringTimeToSeconds(String time) {

        if (time == null || time.isEmpty())
            throw new IllegalArgumentException("time should not be null or empty");

        String[] nums = time.split(":");

        int hour = 0;
        int min = 0;
        int sec = 0;
        if (nums.length == 3) {
            hour = Integer.valueOf(nums[0]);
            min = Integer.valueOf(nums[1]);
            sec = Integer.valueOf(nums[2]);
        }

        if (nums.length == 2) {
            min = Integer.valueOf(nums[0]);
            sec = Integer.valueOf(nums[1]);
        }

        if (nums.length == 1) {
            sec = Integer.valueOf(nums[0]);
        }

        return (hour * 60 * 60) + (min * 60) + sec;

    }

}
