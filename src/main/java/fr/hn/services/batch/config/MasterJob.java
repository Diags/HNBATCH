package fr.hn.services.batch.config;

import fr.hn.services.batch.bean.TransactionDto;
import lombok.Data;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.integration.chunk.RemoteChunkingManagerStepBuilderFactory;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Profile("master")
@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
@PropertySource("classpath:application-master.properties")
@Component
@Data
//@Import({DatabaseConfig.class})
public class MasterJob {
    @Autowired
    private RemoteChunkingManagerStepBuilderFactory remoteChunkingManagerStepBuilderFactory;

    @Autowired
    private KafkaTemplate<String, TransactionDto> masterKafkaTemplate;


    @Bean(name = "jobMaster")
    public Job masterJob(JobRepository jobRepository) {
        return new JobBuilder("jobMaster", jobRepository)
                .incrementer(new RunIdIncrementer())
                // .listener(producerListenerTransacction)
                .start(masterStep())
                .build();
    }

    /*
     * Configure master step components
     */
    @Bean(name = "masterStep")
    public TaskletStep masterStep() {
        return this.remoteChunkingManagerStepBuilderFactory.get("masterStep")
                .chunk(10)
                .reader(masterReader())
                .outputChannel(outboundChannel()) //produces the chunkRequest<Transaction> to kafka wokers
                .inputChannel(inboundChannel()) //consumes the chunkResponse<Transaction> from kafka workers
                // .allowStartIfComplete(Boolean.TRUE)
                // .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public FlatFileItemReader<TransactionDto> masterReader() {
        return new FlatFileItemReaderBuilder<TransactionDto>()
                .resource(new ClassPathResource("/data.csv"))
                .name("Transaction")
                .delimited()
                .delimiter(",")
                .names("id", "name", "transactionDate")
                .linesToSkip(1)  //skipping the header of the file
                .fieldSetMapper(fieldSet -> {
                    TransactionDto data = new TransactionDto();
                    data.setId(fieldSet.readInt("id"));
                    data.setName(fieldSet.readString("name"));
                    data.setTransactionDate(fieldSet.readString("transactionDate"));
                    return data;
                })
                .targetType(TransactionDto.class)
                .build();
    }

    /*
     * Configure outbound flow (requests going to workers)
     */
    @Bean
    public DirectChannel outboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow outboundFlow() {

        var producerMessageHandler = new KafkaProducerMessageHandler<String, TransactionDto>(this.masterKafkaTemplate);
        producerMessageHandler.setTopicExpression(new LiteralExpression("requestsForWokers"));
        //   producerMessageHandler.setPartitionIdExpression(new ValueExpression<>(2)); // two partions

        return IntegrationFlow.from(outboundChannel())
                .log(LoggingHandler.Level.WARN)
                .handle(producerMessageHandler)
                .get();
    }

    /*
     * Configure inbound flow (replies coming from workers)
     */
    @Bean
    public QueueChannel inboundChannel() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow inboundFlow(ConsumerFactory<String, TransactionDto> consumerFactory) {
        return IntegrationFlow.from(Kafka.messageDrivenChannelAdapter(consumerFactory, "repliesFromWokers"))
                .log(LoggingHandler.Level.WARN)
                .channel(inboundChannel())
                .get();
    }
}


