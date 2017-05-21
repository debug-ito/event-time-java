package com.github.debug_ito.event_time.test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import java.time.ZoneId;
import com.github.debug_ito.event_time.EventTime;

public class Synopsis {

    @Test
    public void synopsis() {
        EventTime date = new EventTime(2017, 5, 20, ZoneId.of("+09:00"));
        assertThat(date.getZonedDateTime().toString(), is("2017-05-20T00:00+09:00"));
        assertThat(date.toString(),                    is("2017-05-20+09:00"));
        assertThat(date.isTimeExplicit(),              is(false));
        
        EventTime date_time = new EventTime(2017, 5, 20, 10, 10, 21, 485000000, ZoneId.of("+09:00"));
        assertThat(date_time.getZonedDateTime().toString(), is("2017-05-20T10:10:21.485+09:00"));
        assertThat(date_time.toString(),                    is("2017-05-20T10:10:21.485+09:00"));
        assertThat(date_time.isTimeExplicit(),              is(true));

        EventTime date_zero = new EventTime(2017, 5, 20, 0, 0, 0, 0, ZoneId.of("+09:00"));
        assertThat(date_zero.getZonedDateTime().toString(), is("2017-05-20T00:00+09:00"));
        assertThat(date_zero.toString(),                    is("2017-05-20T00:00+09:00"));
        assertThat(date_zero.isTimeExplicit(),              is(true));

        assertThat(date_zero.compareTo(date) > 0, is(true));
    }

}
