package fr.hn.services.batch.config;

import fr.hn.services.batch.bean.TransactionDto;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.lang.Nullable;

//@Component
public class ProducerListenerTransacction implements ProducerListener<String, TransactionDto> {
    @Override
    public void onSuccess(ProducerRecord<String, TransactionDto> producerRecord, RecordMetadata recordMetadata) {
        System.out.printf(" check lister $$$$ ", producerRecord, recordMetadata);

    }

    @Override
    public void onError(ProducerRecord<String, TransactionDto> producerRecord, @Nullable RecordMetadata recordMetadata, Exception exception) {
        System.out.printf(" check lister $$$$ ", producerRecord, recordMetadata);


    }
}


