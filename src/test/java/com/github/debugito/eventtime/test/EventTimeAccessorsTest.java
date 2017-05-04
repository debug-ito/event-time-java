package com.github.debugito.eventtime.test;
import static org.junit.Assert.assertEquals;
import static com.github.debugito.eventtime.test.Util.et;
import org.junit.Test;
import java.time.ZonedDateTime;
import java.time.ZoneId;

import com.github.debugito.eventtime.EventTime;

public class EventTimeAccessorsTest {
    public static ZonedDateTime zdt(int y, int m, int d, int H, int M, int S, int ns, String zone) {
        return ZonedDateTime.of(y, m, d, H, M, S, ns, ZoneId.of(zone));
    }

    public static ZonedDateTime zdt(int y, int m, int d, int H, int M, int S, int ns) {
        return ZonedDateTime.of(y, m, d, H, M, S, ns, ZoneId.systemDefault());
    }


    public static void test(EventTime etime, ZonedDateTime exp_zdt, boolean exp_is_time_explicit,
                            boolean exp_is_time_zone_explicit) {
        assertEquals(etime.getZonedDateTime(), exp_zdt);
        assertEquals(etime.getZonedDateTime().getZone(), exp_zdt.getZone());
        assertEquals(etime.isTimeExplicit(), exp_is_time_explicit);
        assertEquals(etime.isTimeZoneExplicit(), exp_is_time_zone_explicit);
    }

    @Test
    public void testAccessors() {
        test(et(2017,5,2,"+09:00"), zdt(2017,5,2,0,0,0,0,"+09:00"), false, true);
        test(et(2016,12,23), zdt(2016,12,23,0,0,0,0), false, false);
        test(et(2017,3,6,21,44,10,567,"+09:00"), zdt(2017,3,6,21,44,10,567,"+09:00"), true, true);
        test(et(2017,3,6,21,44,10,567), zdt(2017,3,6,21,44,10,567), true, false);
    }
}
