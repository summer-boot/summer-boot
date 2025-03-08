package io.github.summer.boot.binlog;

import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.source.SourceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Binary log Schema Deserialization
 *
 * @author changebooks@qq.com
 */
public class BinlogDeserialization implements DebeziumDeserializationSchema<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinlogDeserialization.class);

    @Override
    public void deserialize(SourceRecord record, Collector<String> out) {
        if (record == null) {
            LOGGER.error("deserialize failed, record must not be null");
            return;
        }

        String topic = record.topic();
        try {
            Binlog binlog = BinlogParser.parse(record);
            if (binlog != null) {
                out.collect(binlog.toString());
            } else {
                LOGGER.error("deserialize failed, parse failed, binlog must not be null, topic: {}", topic);
            }
        } catch (Throwable ex) {
            LOGGER.error("deserialize failed, topic: {}, throwable: ", topic, ex);
        }
    }

    @Override
    public TypeInformation<String> getProducedType() {
        return BasicTypeInfo.STRING_TYPE_INFO;
    }

}
