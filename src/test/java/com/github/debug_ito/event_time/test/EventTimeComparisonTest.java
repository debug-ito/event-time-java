package com.github.debug_ito.event_time.test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static com.github.debug_ito.event_time.test.Util.et;
import org.junit.Test;

import com.github.debug_ito.event_time.EventTime;

public class EventTimeComparisonTest {
    public void testSingle(EventTime a, EventTime b, int exp_sign) {
        int got_cmp = a.compareTo(b);
        boolean got_eq = a.equals(b);
        if(exp_sign == 0) {
            assertThat(got_cmp, is(0));
            assertThat(got_eq, is(true));
        }else if(exp_sign < 0) {
            assertThat(got_cmp < 0, is(true));
            assertThat(got_eq, is(false));
        }else {
            assertThat(got_cmp > 0, is(true));
            assertThat(got_eq, is(false));
        }
    }
    
    public void test(EventTime a, EventTime b, int exp_sign) {
        testSingle(a, b, exp_sign);
        testSingle(b, a, -exp_sign);
    }

    @Test
    public void testComparisons() {
        test(et(2017,4,26,"+09:00"), et(2017,4,26,"+09:00"), 0);
        test(et(2017,4,26,"+09:00"), et(2017,4,26,"Asia/Tokyo"), 0);
        test(et(2017,4,26,8,13,34,0,"+09:00"), et(2017,4,26,8,13,34,0,"+09:00"), 0);
        test(et(2017,4,26,8,13,34,0,"+09:00"), et(2017,4,26,8,13,34,0,"Asia/Tokyo"), 0);
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
