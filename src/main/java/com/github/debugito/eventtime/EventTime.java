package com.github.debugito.eventtime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.Serializable;
import java.text.ParsePosition;

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

    static private EventTime makeEventTime(LocalDate date, LocalTime time, ZoneId time_zone) {
        if(time == null) {
            return new EventTime(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), time_zone);
        }else {
            return new EventTime(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                                 time.getHour(), time.getMinute(), time.getSecond(), time.getNano(),
                                 time_zone);
        }
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

    /**
     * Compare by internal ZonedDateTime's Instants. Ties are broken
     * by isTimeExplicit flag (flag true is bigger than flag false.)
     */
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
            sb.append('T');
            sb.append(zdt.toLocalTime().toString());
        }
        sb.append(formatZoneId(zdt.getZone()));
        return sb.toString();
    }

    /**
     * Parse string into EventTime.
     *
     * @throws DateTimeParseException if it fails to parse str.
     */
    public static EventTime parse(CharSequence str) {
        ParsePosition pos = new ParsePosition(0);
        LocalDate date = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(str, pos));
        if(str.length() <= pos.getIndex()) {
            return makeEventTime(date, null, null);
        }
        LocalTime time = null;
        if(str.charAt(pos.getIndex()) == 'T') {
            pos.setIndex(pos.getIndex() + 1);
            time = LocalTime.from(DateTimeFormatter.ISO_LOCAL_TIME.parse(str, pos));
        }
        if(str.length() <= pos.getIndex()) {
            return makeEventTime(date, time, null);
        }
        try {
            ZoneId time_zone = parseZone(str.subSequence(pos.getIndex(), str.length()));
            return makeEventTime(date, time, time_zone);
        }catch(DateTimeException e) {
            throw new DateTimeParseException("Failed to parse ZoneId", str, pos.getIndex(), e);
        }
    }

    private static ZoneId parseZone(CharSequence str) {
        if(str.length() == 0) return null;
        if(str.length() > 2 && str.charAt(0) == '[' && str.charAt(str.length()-1) == ']') {
            return ZoneId.of(str.subSequence(1, str.length()-1).toString());
        }
        return ZoneId.of(str.toString());
    }
}
