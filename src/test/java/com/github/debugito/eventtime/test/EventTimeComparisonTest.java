package com.github.debugito.eventtime.test;
import static org.junit.Assert.assertEquals;
import static com.github.debugito.eventtime.test.Util.et;
import org.junit.Test;

import com.github.debugito.eventtime.EventTime;

public class EventTimeComparisonTest {
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
        test(et(2017,4,26,"+09:00"), et(2017,4,26,"+09:00"), 0);
        test(et(2017,4,26,"+09:00"), et(2017,4,26,"JST"), 0);
        test(et(2017,4,26,8,13,34,0,"+09:00"), et(2017,4,26,8,13,34,0,"+09:00"), 0);
        test(et(2017,4,26,8,13,34,0,"+09:00"), et(2017,4,26,8,13,34,0,"JST"), 0);
        test(et(2017,4,26,8,13,34,0,"+09:00"), et(2017,4,25,23,13,34,0,"Z"), 0);

        test(et(2017,5,10,"+09:00"), et(2017,5,9, "+09:00"), 1);
        test(et(2017,5,10,"Z"), et(2017,5,10,"+09:00"), 1);
        test(et(2017,5,3,18,6,23,123,"+09:00"), et(2017,5,3,18,6,0,589,"+09:00"), 1);
        test(et(2017,5,3,18,6,23,123,"Z"), et(2017,5,3,18,6,23,123,"+09:00"), 1);
        
        test(et(2017,7,1,0,0,0,0,"+09:00"), et(2017,5,10,"+09:00"), 1);
        test(et(2017,5,10,10,4,0,0,"+09:00"), et(2017,5,10,"+09:00"), 1);
        test(et(2017,5,10,0,0,0,0,"+09:00"), et(2017,5,10,"+09:00"), 1);
        test(et(2017,5,9,15,0,0,0,"Z"), et(2017,5,10,"+09:00"), 1);
    }
}
