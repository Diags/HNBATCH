package fr.hn.serrvices.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@Slf4j
//@EnableTransactionManagement
@ComponentScan(basePackages = "fr.hn.services.batch.*")
public class BatchApplication implements CommandLineRunner {
    public static void main(String[] args) {

        SpringApplication.run(BatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("START * OK *");
    }


}
