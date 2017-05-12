package com.github.debug_ito.event_time.test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static com.github.debug_ito.event_time.test.Util.et;
import org.junit.Test;

import java.time.DateTimeException;
import java.time.zone.ZoneRulesException;
import java.time.format.DateTimeParseException;

import com.github.debug_ito.event_time.EventTime;

public class EventTimeParserFormattersTest {
    public static void testP(String input, EventTime exp_etime) {
        EventTime got = EventTime.parse(input);
        assertThat(got, is(exp_etime));
        assertThat(got.getZonedDateTime(), is(exp_etime.getZonedDateTime()));
        assertThat(got.isTimeExplicit(), is(exp_etime.isTimeExplicit()));
        assertThat(got.isTimeZoneExplicit(), is(exp_etime.isTimeZoneExplicit()));
        assertThat(got.getZonedDateTime().getZone(), is(exp_etime.getZonedDateTime().getZone()));
    }

    public static void testPE(String input) {
        Exception excep = null;
        try {
            EventTime.parse(input);
        }catch(DateTimeParseException e) {
            excep = e;
        }
        assertThat(excep, is(notNullValue()));
    }

    @Test
    public void testParser() {
        testP("2017-04-20", et(2017,4,20));
        testP("2016-10-31Z", et(2016,10,31,"Z"));
        testP("2016-04-23+00:00", et(2016,4,23,"Z"));
        testP("2016-09-11+09:00", et(2016,9,11,"+09:00"));
        testP("2016-09-10[Asia/Tokyo]", et(2016,9,10,"Asia/Tokyo"));
        testP("2016-11-24T19:04:00", et(2016,11,24,19,4,0,0));
        testP("2017-09-12T19:12+02:00", et(2017,9,12,19,12,0,0,"+02:00"));
        testP("2016-11-22T04:00:32.903", et(2016,11,22,4,0,32,903000000));
        testP("2017-01-10T00:52:10+09:00", et(2017,1,10,0,52,10,0,"+09:00"));
        testP("2016-07-31T22:10:08.405Z", et(2016,7,31,22,10,8,405000000,"Z"));
        testP("2015-01-04T00:00:08.405396Z", et(2015,1,4,0,0,8,405396000,"Z"));
        testP("2007-12-03T10:15:30[Europe/Paris]", et(2007,12,3,10,15,30,0,"Europe/Paris"));
        testP("2015-06-11T00:14:09.045[Asia/Tokyo]", et(2015,6,11,0,14,9,45000000,"Asia/Tokyo"));
        testP("2011-09-01T13:00+09:00", et(2011,9,1,13,0,0,0,"+09:00"));
    }

    @Test
    public void testParserErrors() {
        testPE("20");
        testPE("2017-1112");
        testPE("abc");
        testPE("2017--08-12");
        testPE("2017-08-12T10");
        testPE("2017-09-12T19:12:");
        testPE("2017-11-19T11:12:19...");
        testPE("2015-09-02X");
        testPE("2011-11-10+abc");
        testPE("2015-12-30[ABCDE]");
        testPE("2015-12-30[ABCDEF");
        testPE("2014-12-10T10:12:44.313943[Hoge/Foobar]");
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
        testF(et(2015,1,4,0,0,8,405396000,"Z"), "2015-01-04T00:00:08.405396Z");
        testF(et(2007,12,3,10,15,30,0,"Europe/Paris"), "2007-12-03T10:15:30[Europe/Paris]");
        testF(et(2015,6,11,0,14,9,45000000,"Asia/Tokyo"), "2015-06-11T00:14:09.045[Asia/Tokyo]");
        testF(et(2016,10,11,9,40,12,0,"Asia/Tokyo"), "2016-10-11T09:40:12[Asia/Tokyo]");
        testF(et(2011,9,1,13,0,0,0,"+09:00"), "2011-09-01T13:00+09:00");
    }
}
