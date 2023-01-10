package fr.hn.services.batch.config;

import fr.hn.services.batch.bean.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.chunk.RemoteChunkingWorkerBuilder;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Profile("woker")
@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
@PropertySource("classpath:application-worker.properties")
//@Import({DatabaseConfig.class})
@Slf4j
@Component
public class WokerJob {

    @Autowired
    private RemoteChunkingWorkerBuilder<TransactionDto, TransactionDto> remoteChunkingWorkerBuilder;

    @Autowired
    private KafkaTemplate<String, TransactionDto> transactionKafkaTemplate;

    @Autowired
    private DataSource dataSource;

    @Bean
    public IntegrationFlow workerStep() {
        return this.remoteChunkingWorkerBuilder
                .inputChannel(inboundChannel()) //consumes the chunkRequest<Transaction> from kafka wokers
                .outputChannel(outboundChannel()) //produces the chunkResponse<Transaction> to kafka master
                .itemProcessor(itemProcessor())
                .itemWriter(itemWriter())
                .build();

    }

    /*
     * Configure worker components
     */
    @Bean
    public ItemProcessor<TransactionDto, TransactionDto> itemProcessor() {
        return item -> {
            System.out.println("processing item " + item);
            return item;
        };
    }

    @Bean
    public ItemWriter<TransactionDto> itemWriter() {
        return new JdbcBatchItemWriterBuilder<TransactionDto>()
                .beanMapped()
                .dataSource(dataSource)
                .sql("INSERT INTO TRANSACTDTO (id,name,transactionDate) VALUES (:id, :name, :transactionDate)")
                .build();
    }

    /*
     * Configure inbound flow (requests coming from the manager)
     */
    @Bean
    public DirectChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow inboundFlow(ConsumerFactory<String, TransactionDto> consumerFactory) {
        return IntegrationFlow.from(Kafka.messageDrivenChannelAdapter(consumerFactory, "requestsForWokers"))
                .log(LoggingHandler.Level.WARN)
                .channel(inboundChannel())
                .get();
    }

    /*
     * Configure outbound flow (replies going to the manager)
     */
    @Bean
    public DirectChannel outboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow outboundFlow() {
        var producerMessageHandler = new KafkaProducerMessageHandler<String, TransactionDto>(transactionKafkaTemplate);
        producerMessageHandler.setTopicExpression(new LiteralExpression("repliesFromWokers"));
        return IntegrationFlow.from(outboundChannel())
                .log(LoggingHandler.Level.WARN)
                .handle(producerMessageHandler)
                .get();
    }
}
