package com.github.debug_ito.event_time.titan;
import com.github.debug_ito.event_time.EventTime;
import com.thinkaurelius.titan.core.attribute.AttributeSerializer;
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
    @Override
    public EventTime read(ScanBuffer buf) {
        // TODO
        return null;
    }

    @Override
    public void write(WriteBuffer buf, EventTime et) {
        // TODO
    }
}
