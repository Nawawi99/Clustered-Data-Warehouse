package dev.awn.datawarehouse.core.deal.service.impl;

import dev.awn.datawarehouse.common.exception.DuplicateDealException;
import dev.awn.datawarehouse.common.exception.MissingBodyFieldsException;
import dev.awn.datawarehouse.core.deal.controller.DealController;
import dev.awn.datawarehouse.core.deal.document.Deal;
import dev.awn.datawarehouse.core.deal.dto.DealDTO;
import dev.awn.datawarehouse.core.deal.mapper.DealMapper;
import dev.awn.datawarehouse.core.deal.repository.DealRepository;
import dev.awn.datawarehouse.core.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Service
public class DealServiceImpl implements DealService {
    private final DealRepository dealRepository;
    private final DealMapper dealMapper;
    private final Logger logger = LoggerFactory.getLogger(DealServiceImpl.class);

    @Override
    public DealDTO persistDeal(DealDTO dealDTO, BindingResult bindingResult) {
        logger.info("into -> persistDeal method");

        if(bindingResult.hasErrors()) {
            logger.error("bindingResult contains errors, throwing MissingBodyFieldsException");

            throw new MissingBodyFieldsException(bindingResult);
        }

        String dealId = dealDTO.getId();
        if(dealId != null) {
            logger.info("Found Deal of ID {}, checking if it exists", dealId);

            boolean dealExists = dealRepository.existsById(dealId);

            if(dealExists) {
                logger.error("Deal of ID {} exists, throwing DuplicateDealException", dealId);

                throw new DuplicateDealException();
            }
        }

        // Ignoring the ID in the mapping process, to ensure consistent IDs for DB vendor
        Deal deal = dealMapper.toDocument(dealDTO);

        logger.info("Attempting to persist Deal document");

        Deal savedDeal = dealRepository.save(deal);

        logger.info("Deal of ID {} was successfully persisted", savedDeal.getId());

        return dealMapper.toDTO(savedDeal);
    }
}
