package br.com.confluent.stream.topoly;

import br.com.confluence.events.Output;
import br.com.confluent.stream.serdes.AppSerdes;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.time.Instant;

@Configuration
@EnableKafkaStreams
@Data
@Slf4j
public class StreamProcess {

    private final AppSerdes appSerdes;

    public StreamProcess(AppSerdes appSerdes) {
        this.appSerdes = appSerdes;
    }

    @Bean
    public Topology processEvent(final StreamsBuilder streamsBuilder) {
        streamsBuilder.stream("input-topic", Consumed.with(Serdes.String(), appSerdes.inputSerde()))
                .filter((key, value) -> value.getName().equals("INPUT-TEST"))
                .map((key, value) -> new KeyValue<>(key, Output.newBuilder()
                        .setId(value.getId())
                        .setName("OUTPUT-TEST")
                        .setDate(Instant.now().toString())
                        .build())
                ).to("output-topic", Produced.with(Serdes.String(), appSerdes.outputSerde()));

        return streamsBuilder.build();
    }

}
