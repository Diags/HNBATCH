package fr.hn.services.batch.config;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionPartionner implements Partitioner {

    private static final String PARTITION_KEY = "partition";

    /**
     * we can retrieve the name of the files in the reading directory and partition them into several partitions
     *
     * @param gridSize
     * @return
     */

    @Override
    public Map<String, org.springframework.batch.item.ExecutionContext> partition(int gridSize) {
        Map<String, org.springframework.batch.item.ExecutionContext> map = new HashMap<>(gridSize);
        for (int i = 0; i < gridSize; i++) {
            map.put(PARTITION_KEY + i, new ExecutionContext());
        }
        return map;
    }

}
