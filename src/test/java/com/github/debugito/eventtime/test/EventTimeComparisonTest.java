package com.github.debugito.eventtime.test;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.github.debugito.eventtime.EventTime;

public class EventTimeComparisonTest {
    public static EventTime et(int year, int month, int day_of_month) {
        return new EventTime(year, month, day_of_month);
    }

    public static EventTime et(int year, int month, int day_of_month,
                               int hour, int minute, int second, int nanosecond,
                               String zone_id) {
        return new EventTime(year, month, day_of_month, hour, minute, second, nanosecond, zone_id);
    }

    public void testSingle(EventTime a, EventTime b, int exp_sign) {
        int got_cmp = a.compareTo(b);
        boolean got_eq = a.equals(b);
        if(exp_sign == 0) {
            assertEquals(got_cmp, 0);
            assertEquals(got_eq, true);
        }else if(exp_sign < 0) {
            assertEquals(got_cmp < 0, true);
            assertEquals(got_eq, false);
        }else {
            assertEquals(got_cmp > 0, true);
            assertEquals(got_eq, false);
        }
    }
    
    public void test(EventTime a, EventTime b, int exp_sign) {
        testSingle(a, b, exp_sign);
        testSingle(b, a, -exp_sign);
    }

    @Test
    public void testComparisons() {
        test(et(2017,10,11), et(2017,10,11), 0);
        test(et(2017,10,11,8,13,34,0,"+0900"), et(2017,10,11,8,13,34,0,"+0900"), 0);

        test(et(2017,10,10), et(2017,10,9), 1);
        test(et(2017,10,10), et(2017,3,30), 1);
        test(et(2017,10,10), et(2020,8,15), 1);
    }
}
