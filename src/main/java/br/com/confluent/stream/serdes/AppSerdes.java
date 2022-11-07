package br.com.confluent.stream.serdes;

import br.com.confluence.events.Input;
import br.com.confluence.events.Output;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import lombok.Data;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Data
public class AppSerdes extends Serdes {


    @Value("${spring.kafka.streams.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Value("${spring.kafka.streams.properties.schema.registry.basic.auth.user.info}")
    private String schemaRegistryAuth;


    public Serde<Input> inputSerde(){
        final Serde<Input> serde = new SpecificAvroSerde<>();

        Map<String, Object> serdeConfigsInput = new HashMap<>();
        serdeConfigsInput.put("schema.registry.url", schemaRegistryUrl);
        serdeConfigsInput.put("schema.registry.basic.auth.user.info", schemaRegistryAuth);
        serdeConfigsInput.put("basic.auth.credentials.source", "USER_INFO");
        serde.configure(serdeConfigsInput, false);

        return serde;
    }

    public Serde<Output> outputSerde(){
        final Serde<Output> serde = new SpecificAvroSerde<>();

        Map<String, Object> serdeConfigsOutput = new HashMap<>();
        serdeConfigsOutput.put("schema.registry.url", schemaRegistryUrl);
        serdeConfigsOutput.put("schema.registry.basic.auth.user.info", schemaRegistryAuth);
        serdeConfigsOutput.put("basic.auth.credentials.source", "USER_INFO");
        serde.configure(serdeConfigsOutput, false);

        return serde;
    }

}
