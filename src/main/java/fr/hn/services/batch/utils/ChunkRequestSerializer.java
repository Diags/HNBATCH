package fr.hn.services.batch.utils;

import fr.hn.services.batch.bean.TransactionDto;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.batch.integration.chunk.ChunkRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

@Component
public class ChunkRequestSerializer implements Serializer<ChunkRequest<TransactionDto>> {
    @Override
    public byte[] serialize(String s, ChunkRequest<TransactionDto> chunkRequest) {
        if (chunkRequest == null) {
            return new byte[0];
        }
        return SerializationUtils.serialize(chunkRequest);
    }
}
