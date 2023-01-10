package fr.hn.services.batch.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicKafkaConfig {

/*   @Bean
    public NewTopic requestsForWokers() {
        return TopicBuilder.name("requestsForWokers")
                .partitions(2)
                .build();
    }

   @Bean
    public NewTopic repliesFromWokers() {
        return TopicBuilder.name("repliesFromWokers")
                .partitions(2)
                .build();
    }*/
}
