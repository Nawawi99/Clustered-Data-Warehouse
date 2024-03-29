package dev.awn.datawarehouse.core.deal.mapper;

import dev.awn.datawarehouse.core.deal.document.Deal;
import dev.awn.datawarehouse.core.deal.dto.DealDTO;
import org.springframework.stereotype.Component;

@Component
public class DealMapper {

    public Deal toDocument(DealDTO dealDTO) {
        return Deal.builder()
                   .fromCurrencyISO(dealDTO.getFromCurrencyISO())
                   .toCurrencyISO(dealDTO.getToCurrencyISO())
                   .timestamp(dealDTO.getTimestamp())
                   .amount(dealDTO.getAmount())
                   .build();
    }

    public DealDTO toDTO(Deal deal) {
        return DealDTO.builder()
                      .id(deal.getId())
                      .fromCurrencyISO(deal.getFromCurrencyISO())
                      .toCurrencyISO(deal.getToCurrencyISO())
                      .timestamp(deal.getTimestamp())
                      .amount(deal.getAmount())
                      .build();
    }

}
