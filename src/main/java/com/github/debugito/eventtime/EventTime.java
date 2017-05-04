package com.github.debugito.eventtime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.io.Serializable;

public class EventTime implements Comparable<EventTime>, Serializable {
    /**
     * Create an EventTime with non-explicit time part. If `time_zone`
     * is `null`, the system default (`ZoneId.systemDefault()`) is
     * used instead. 
     */
    public EventTime(int year, int month, int day, ZoneId time_zone) {
        // TODO
    }

    /**
     * Alias for `EventTime(year, month, day, null)`
     */
    public EventTime(int year, int month, int day) {
        // TODO
    }

    /**
     * Create an EventTime with explicit time part.  If `time_zone` is
     * `null`, the system default (`ZoneId.systemDefault()`) is used
     * instead.
     */
    public EventTime(int year, int month, int day,
                     int hour, int minute, int second, int nanosecond, ZoneId time_zone) {
        // TODO
    }

    /**
     * Alias for `EventTime(year, month, day, hour, minute, second, nanosecond, null)`
     */
    public EventTime(int year, int month, int day,
                     int hour, int minute, int second, int nanosecond) {
        // TODO
    }
 
    /**
     * Create an EventTime from a ZonedDateTime. The time part and the
     * time zone are both considered explicit. `zdt` must be non-null.
     */
    public EventTime(ZonedDateTime zdt) {
        // TODO
    }

    private EventTime(ZonedDateTime zdt, boolean is_time_explicit) {
        // TODO
    }

    /**
     * Get the `ZonedDateTime` part.
     */
    public ZonedDateTime getZonedDateTime() {
        // TODO
        return null;
    }

    /**
     * Get the flag that indicates the time part is explicitly given
     * or not.
     */
    public boolean isTimeExplicit() {
        // TODO
        return false;
    }

    /**
     * Get the flag that indicates the time zone is explicitly given
     * or not.
     */
    public boolean isTimeZoneExplicit() {
        // TODO
        return false;
    }

    @Override
    public int compareTo(EventTime that) {
        // TODO
        return 0;
    }
    
    @Override
    public boolean equals(Object that) {
        // TODO
        return false;
    }

    @Override
    public int hashCode() {
        // TODO
        return 0;
    }

    @Override
    public String toString() {
        // TODO
        return "";
    }

    public static EventTime parse(CharSequence str) {
        // TODO
        return null;
    }
}
