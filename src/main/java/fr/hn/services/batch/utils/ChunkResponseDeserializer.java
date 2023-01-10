package fr.hn.services.batch.utils;

import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.batch.integration.chunk.ChunkResponse;
import org.springframework.core.serializer.DefaultDeserializer;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class ChunkResponseDeserializer implements Deserializer<ChunkResponse> {
    @Override
    public ChunkResponse deserialize(String s, byte[] bytes) {
        try {
            if (bytes == null) {
                return null;
            }

            return (ChunkResponse) new DefaultDeserializer().deserialize(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
