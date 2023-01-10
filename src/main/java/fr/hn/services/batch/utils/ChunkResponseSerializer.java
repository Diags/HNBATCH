package fr.hn.services.batch.utils;

import org.apache.kafka.common.serialization.Serializer;
import org.springframework.batch.integration.chunk.ChunkResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

@Component
public class ChunkResponseSerializer implements Serializer<ChunkResponse> {
    @Override
    public byte[] serialize(String s, ChunkResponse chunkResponse) {
        return SerializationUtils.serialize(chunkResponse);
    }
}
