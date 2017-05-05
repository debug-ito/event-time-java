package com.github.debugito.eventtime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.io.Serializable;

public class EventTime implements Comparable<EventTime>, Serializable {
    private ZonedDateTime zdt;
    private boolean is_time_explicit;
    private boolean is_time_zone_explicit;
    
    /**
     * Create an EventTime with non-explicit time part. If `time_zone`
     * is `null`, the system default (`ZoneId.systemDefault()`) is
     * used instead. 
     */
    public EventTime(int year, int month, int day, ZoneId time_zone) {
        this(year, month, day, 0, 0, 0, 0, time_zone);
        is_time_explicit = false;
    }

    /**
     * Alias for `EventTime(year, month, day, null)`
     */
    public EventTime(int year, int month, int day) {
        this(year, month, day, null);
    }

    /**
     * Create an EventTime with explicit time part.  If `time_zone` is
     * `null`, the system default (`ZoneId.systemDefault()`) is used
     * instead.
     */
    public EventTime(int year, int month, int day,
                     int hour, int minute, int second, int nanosecond, ZoneId time_zone) {
        if(time_zone == null) {
            time_zone = ZoneId.systemDefault();
            is_time_zone_explicit = false;
        }else {
            is_time_zone_explicit = true;
        }
        zdt = ZonedDateTime.of(year, month, day, hour, minute, second, nanosecond, time_zone);
        is_time_explicit = true;
    }

    /**
     * Alias for `EventTime(year, month, day, hour, minute, second, nanosecond, null)`
     */
    public EventTime(int year, int month, int day,
                     int hour, int minute, int second, int nanosecond) {
        this(year, month, day, hour, minute, second, nanosecond, null);
    }
 
    /**
     * Create an EventTime from a ZonedDateTime. The time part and the
     * time zone are both considered explicit. `zdt` must be non-null.
     */
    public EventTime(ZonedDateTime zdt) {
        this.zdt = zdt;
        is_time_explicit = true;
        is_time_zone_explicit = true;
    }

    /**
     * Get the `ZonedDateTime` part.
     */
    public ZonedDateTime getZonedDateTime() {
        return zdt;
    }

    /**
     * Get the flag that indicates the time part is explicitly given
     * or not.
     */
    public boolean isTimeExplicit() {
        return is_time_explicit;
    }

    /**
     * Get the flag that indicates the time zone is explicitly given
     * or not.
     */
    public boolean isTimeZoneExplicit() {
        return is_time_zone_explicit;
    }

    @Override
    public int compareTo(EventTime that) {
        int instant_comp = this.zdt.toInstant().compareTo(that.zdt.toInstant());
        if(instant_comp != 0) return instant_comp;
        return (this.is_time_explicit && !that.is_time_explicit) ? 1
            :  (!this.is_time_explicit && that.is_time_explicit) ? -1
            : 0;
    }
    
    @Override
    public boolean equals(Object that) {
        if(!(that instanceof EventTime)) {
            return false;
        }
        EventTime et_that = (EventTime)that;
        return (this.compareTo(et_that) == 0);
    }

    @Override
    public int hashCode() {
        return zdt.hashCode() + new Boolean(is_time_explicit).hashCode();
    }

    private static String formatZoneId(ZoneId zone_id) {
        String zone = zone_id.toString();
        if(zone.length() == 0) return zone;
        char head = zone.charAt(0);
        if(head == '+' || head == '-' || head == 'Z') {
            return zone;
        }else {
            return "[" + zone + "]";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(zdt.toLocalDate().toString());
        if(is_time_explicit) {
            sb.append(zdt.toLocalTime().toString());
        }
        sb.append(formatZoneId(zdt.getZone()));
        return sb.toString();
    }

    public static EventTime parse(CharSequence str) {
        // TODO
        return null;
    }
}
