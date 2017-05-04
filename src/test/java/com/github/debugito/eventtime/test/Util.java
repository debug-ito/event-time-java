package com.github.debugito.eventtime.test;
import java.time.ZoneId;

import com.github.debugito.eventtime.EventTime;

public class Util {
    public static EventTime et(int year, int month, int day_of_month, String zone_id) {
        return new EventTime(year, month, day_of_month, ZoneId.of(zone_id));
    }

    public static EventTime et(int year, int month, int day_of_month) {
        return new EventTime(year, month, day_of_month);
    }

    public static EventTime et(int year, int month, int day_of_month,
                               int hour, int minute, int second, int nanosecond,
                               String zone_id) {
        return new EventTime(year, month, day_of_month, hour, minute, second, nanosecond,
                             ZoneId.of(zone_id));
    }

    public static EventTime et(int year, int month, int day_of_month,
                               int hour, int minute, int second, int nanosecond) {
        return new EventTime(year, month, day_of_month, hour, minute, second, nanosecond);
    }
}
