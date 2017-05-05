package com.github.debugito.eventtime.test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static com.github.debugito.eventtime.test.Util.et;
import org.junit.Test;

import com.github.debugito.eventtime.EventTime;

public class EventTimeParserFormattersTest {
    public static void testP(String input, EventTime exp_etime) {
        EventTime got = EventTime.parse(input);
        assertThat(got, is(exp_etime));
        assertThat(got.getZonedDateTime(), is(exp_etime.getZonedDateTime()));
        assertThat(got.isTimeExplicit(), is(exp_etime.isTimeExplicit()));
        assertThat(got.isTimeZoneExplicit(), is(exp_etime.isTimeZoneExplicit()));
        assertThat(got.getZonedDateTime().getZone(), is(exp_etime.getZonedDateTime().getZone()));
    }

    @Test
    public void testParser() {
        testP("2017-04-20", et(2017,4,20));
        testP("2016-10-31Z", et(2016,10,31,"Z"));
        testP("2016-04-23+00:00", et(2016,4,23,"Z"));
        testP("2016-09-11+09:00", et(2016,9,11,"+09:00"));
        testP("2016-11-22+09:00[Asia/Tokyo]", et(2016,11,22,"Asia/Tokyo"));
        testP("2016-09-10[Asia/Tokyo]", et(2016,9,10,"Asia/Tokyo"));
        testP("2016-11-24T19:04:00", et(2016,11,24,19,4,0,0));
        testP("2016-11-22T04:00:32.903", et(2016,11,22,4,0,32,903000000));
        testP("2017-01-10T00:52:10+09:00", et(2017,1,10,0,52,10,0,"+09:00"));
        testP("2016-07-31T22:10:08.405Z", et(2016,7,31,22,10,8,405000000,"Z"));
        testP("2007-12-03T10:15:30+01:00[Europe/Paris]", et(2007,12,3,10,15,30,0,"Europe/Paris"));
        testP("2015-06-11T00:14:09.045+09:00[Asia/Tokyo]", et(2015,6,11,0,14,9,45000000,"Asia/Tokyo"));
        testP("2016-10-11T09:40:12[Asia/Tokyo]", et(2016,10,11,9,40,12,0,"Asia/Tokyo"));
    }

    public static void testF(EventTime etime, String exp_output) {
        assertThat(etime.toString(), is(exp_output));
    }

    @Test
    public void testFormatter() {
        testF(et(2016,10,31, "Z"), "2016-10-31Z");
        testF(et(2016,4,23,"+00:00"), "2016-04-23Z");
        testF(et(2016,9,11,"+09:00"), "2016-09-11+09:00");
        testF(et(2016,11,22,"Asia/Tokyo"), "2016-11-22[Asia/Tokyo]");
        testF(et(2017,1,10,0,52,10,0,"+09:00"), "2017-01-10T00:52:10+09:00");
        testF(et(2016,7,31,22,10,8,405000000,"Z"), "2016-07-31T22:10:08.405Z");
        testF(et(2007,12,3,10,15,30,0,"Europe/Paris"), "2007-12-03T10:15:30[Europe/Paris]");
        testF(et(2015,6,11,0,14,9,45000000,"Asia/Tokyo"), "2015-06-11T00:14:09.045[Asia/Tokyo]");
        testF(et(2016,10,11,9,40,12,0,"Asia/Tokyo"), "2016-10-11T09:40:12[Asia/Tokyo]");
    }
}
