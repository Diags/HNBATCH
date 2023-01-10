package fr.hn.services.batch.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Getter
@Setter
@ToString
public class TransactionDto implements Serializable {
    private int id;
    private String name;
    private String transactionDate;
}
