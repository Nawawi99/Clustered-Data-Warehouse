package dev.awn.datawarehouse.core.deal.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(value = "deals")
public class Deal {
    @Id
    private String id;
    private String fromCurrencyISO;
    private String toCurrencyISO;
    private LocalDateTime timestamp;
    private Double amount;
}
