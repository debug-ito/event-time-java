package com.github.debug_ito.event_time.titan.test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.time.ZoneId;

import com.github.debug_ito.event_time.EventTime;
import com.github.debug_ito.event_time.titan.EventTimeSerializer;

import com.thinkaurelius.titan.graphdb.database.serialize.DataOutput;
import com.thinkaurelius.titan.graphdb.database.serialize.Serializer;
import com.thinkaurelius.titan.graphdb.database.serialize.StandardSerializer;
import com.thinkaurelius.titan.diskstorage.ReadBuffer;

public class EventTimeSerializerTest {
    // See https://github.com/pluradj/titan-attribute-serializer/blob/master/src/test/java/pluradj/titan/graphdb/database/serialize/attribute/StringBufferSerializerTest.java
    private void check(EventTime input_et,
                       BiConsumer<DataOutput, EventTime> writer,
                       BiFunction<ReadBuffer, Serializer, EventTime> reader) {
        StandardSerializer serialize = new StandardSerializer();
        serialize.registerClass(1, EventTime.class, new EventTimeSerializer());
        assertThat(serialize.validDataType(EventTime.class), is(true));

        DataOutput out = serialize.getDataOutput(128);
        writer.accept(out, input_et);
        ReadBuffer read_buf = out.getStaticBuffer().asReadBuffer();
        EventTime got = reader.apply(read_buf, serialize);

        assertThat(got, is(input_et));
    }

    @Test
    public void writeAndRead() {
        BiConsumer<DataOutput, EventTime> not_null_writer =
            (DataOutput out, EventTime et) -> { out.writeObjectNotNull(et); };
        BiFunction<ReadBuffer, Serializer, EventTime> not_null_reader =
            (ReadBuffer buf, Serializer ser) -> { return ser.readObjectNotNull(buf, EventTime.class); };
        
        BiConsumer<DataOutput, EventTime> class_writer =
            (DataOutput out, EventTime et) -> { out.writeClassAndObject(et); };
        BiFunction<ReadBuffer, Serializer, EventTime> class_reader =
            (ReadBuffer buf, Serializer ser) -> { return (EventTime)ser.readClassAndObject(buf); };

        BiConsumer<DataOutput, EventTime> object_writer =
            (DataOutput out, EventTime et) -> { out.writeObject(et, EventTime.class); };
        BiFunction<ReadBuffer, Serializer, EventTime> object_reader =
            (ReadBuffer buf, Serializer ser) -> { return ser.readObject(buf, EventTime.class); };

        EventTime target = new EventTime(2017, 7, 3, ZoneId.of("+09:00"));
        check(target, not_null_writer, not_null_reader);
        check(target, class_writer, class_reader);
        check(target, object_writer, object_reader);

        target = new EventTime(2017, 3, 31, 10, 39, 11, 19203, ZoneId.of("-10:00"));
        check(target, not_null_writer, not_null_reader);
        check(target, class_writer, class_reader);
        check(target, object_writer, object_reader);
    }
}
