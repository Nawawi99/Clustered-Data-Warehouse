package dev.awn.datawarehouse.core.deal.mapper;

import dev.awn.datawarehouse.core.deal.document.Deal;
import dev.awn.datawarehouse.core.deal.dto.DealDTO;
import dev.awn.datawarehouse.core.deal.service.impl.DealServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DealMapper {
    private final Logger logger = LoggerFactory.getLogger(DealMapper.class);

    public Deal toDocument(DealDTO dealDTO) {
        logger.info("into -> toDocument method");

        logger.info("Attempting to convert DealDTO to Deal document");

        return Deal.builder()
                   .fromCurrencyISO(dealDTO.getFromCurrencyISO())
                   .toCurrencyISO(dealDTO.getToCurrencyISO())
                   .timestamp(dealDTO.getTimestamp())
                   .amount(dealDTO.getAmount())
                   .build();
    }

    public DealDTO toDTO(Deal deal) {
        logger.info("into -> toDTO method");

        logger.info("Attempting to convert Deal document to DealDTO");

        return DealDTO.builder()
                      .id(deal.getId())
                      .fromCurrencyISO(deal.getFromCurrencyISO())
                      .toCurrencyISO(deal.getToCurrencyISO())
                      .timestamp(deal.getTimestamp())
                      .amount(deal.getAmount())
                      .build();
    }

}
