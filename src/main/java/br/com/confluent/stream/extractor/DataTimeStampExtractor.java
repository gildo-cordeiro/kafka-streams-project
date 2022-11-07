package br.com.confluent.stream.extractor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class DataTimeStampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        return record.timestamp() > 0 ? record.timestamp() : partitionTime;
    }
}
