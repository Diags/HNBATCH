package fr.hn.serrvices.batch.listener;

import fr.hn.services.batch.bean.TransactionDto;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
public class JobListener implements JobExecutionListener, ChunkListener, ItemReadListener<TransactionDto>, ItemWriteListener<TransactionDto>, ItemProcessListener<TransactionDto, TransactionDto>, StepExecutionListener {

    @Override
    public void beforeChunk(ChunkContext context) {
// check somme logic of each partition
    }

    @Override
    public void afterChunk(ChunkContext context) {
// check somme logic of each partition
    }

    @Override
    public void afterChunkError(ChunkContext context) {
// check somme logic of each partition
    }

    @Override
    public void beforeProcess(TransactionDto item) {
// check somme logic of each partition process
    }

    @Override
    public void afterProcess(TransactionDto item, TransactionDto result) {

    }

    @Override
    public void onProcessError(TransactionDto item, Exception e) {
// some logic here
    }

    @Override
    public void beforeRead() {
// check some rules  like if headers of file are ok
    }

    @Override
    public void afterRead(TransactionDto item) {
// do something if needed
    }

    @Override
    public void onReadError(Exception ex) {
        //  handle error reader
    }

    @Override
    public void beforeWrite(Chunk<? extends TransactionDto> items) {
        // add somme rules
    }

    @Override
    public void afterWrite(Chunk<? extends TransactionDto> items) {
        //put somme logic after writting
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends TransactionDto> items) {
        // handle error writting
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // check if we can go to database for somme process.
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // we can create en resume file
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

}
