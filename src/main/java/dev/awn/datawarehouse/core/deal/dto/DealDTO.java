package dev.awn.datawarehouse.core.deal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DealDTO {
    private String id;

    @NotNull(message = "Field [fromCurrencyISO] cannot be null")
    @NotBlank(message = "Field [fromCurrencyISO] cannot be empty")
    private String fromCurrencyISO;

    @NotNull(message = "Field [toCurrencyISO] cannot be null")
    @NotBlank(message = "Field [toCurrencyISO] cannot be empty")
    private String toCurrencyISO;

    @NotNull(message = "Field [timestamp] cannot be null")
    private LocalDateTime timestamp;

    @NotNull(message = "Field [amount] cannot be null")
    private Double amount;
}
