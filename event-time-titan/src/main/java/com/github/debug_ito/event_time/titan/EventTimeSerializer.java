package com.github.debug_ito.event_time.titan;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import com.github.debug_ito.event_time.EventTime;
import com.thinkaurelius.titan.core.attribute.AttributeSerializer;
import com.thinkaurelius.titan.graphdb.database.serialize.attribute.IntegerSerializer;
import com.thinkaurelius.titan.graphdb.database.serialize.attribute.StringSerializer;
import com.thinkaurelius.titan.graphdb.database.serialize.attribute.BooleanSerializer;
import com.thinkaurelius.titan.diskstorage.ScanBuffer;
import com.thinkaurelius.titan.diskstorage.WriteBuffer;

/**
 * EventTime's AttributeSerializer for Titan graph database.
 *
 * See:
 *
 * - http://s3.thinkaurelius.com/docs/titan/1.0.0/serializer.html
 * - https://github.com/pluradj/titan-attribute-serializer
 */
public class EventTimeSerializer implements AttributeSerializer<EventTime> {
    private IntegerSerializer ser_int;
    private StringSerializer ser_str;
    private BooleanSerializer ser_bool;

    public EventTimeSerializer() {
        ser_int = new IntegerSerializer();
        ser_str = new StringSerializer();
        ser_bool = new BooleanSerializer();
    }
    
    @Override
    public EventTime read(ScanBuffer buf) {
        boolean is_time_explicit = ser_bool.read(buf);
        int year = ser_int.read(buf);
        int month = ser_int.read(buf);
        int day = ser_int.read(buf);
        ZoneId zone = ZoneId.of(ser_str.read(buf));
        if(!is_time_explicit) {
            return new EventTime(year, month, day, zone);
        }else {
            int hour = ser_int.read(buf);
            int min = ser_int.read(buf);
            int sec = ser_int.read(buf);
            int nano = ser_int.read(buf);
            return new EventTime(year, month, day, hour, min, sec, nano, zone);
        }
    }

    @Override
    public void write(WriteBuffer buf, EventTime et) {
        ser_bool.write(buf, et.isTimeExplicit());
        ZonedDateTime zdt = et.getZonedDateTime();
        ser_int.write(buf, zdt.getYear());
        ser_int.write(buf, zdt.getMonthValue());
        ser_int.write(buf, zdt.getDayOfMonth());
        ser_str.write(buf, zdt.getZone().toString());
        if(et.isTimeExplicit()) {
            ser_int.write(buf, zdt.getHour());
            ser_int.write(buf, zdt.getMinute());
            ser_int.write(buf, zdt.getSecond());
            ser_int.write(buf, zdt.getNano());
        }
    }
}
