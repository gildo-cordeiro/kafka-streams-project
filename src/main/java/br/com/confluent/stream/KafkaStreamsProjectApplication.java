package br.com.confluent.stream;

import br.com.confluence.events.Input;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;
import java.util.UUID;

@SpringBootApplication
public class KafkaStreamsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsProjectApplication.class, args);
    }

    @Bean
    public NewTopic inputTopic(){
        return TopicBuilder.name("input-topic")
                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic outputTopic(){
        return TopicBuilder.name("output-topic")
                .partitions(3)
                .build();
    }


    @Bean
    public int send(KafkaTemplate<String, Input> template) {
        var input = Input.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setName("INPUT-TEST")
                .setDate(Instant.now().toString())
                .build();

            template.send("input-topic", "444d9290-64c3-4aeb-85b7-14afb1599215", input);
            return 1;
    }
}
