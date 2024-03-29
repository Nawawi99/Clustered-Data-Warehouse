package dev.awn.datawarehouse.core.deal.service.impl;

import dev.awn.datawarehouse.common.exception.DuplicateDealException;
import dev.awn.datawarehouse.common.exception.MissingBodyFieldsException;
import dev.awn.datawarehouse.core.deal.document.Deal;
import dev.awn.datawarehouse.core.deal.dto.DealDTO;
import dev.awn.datawarehouse.core.deal.mapper.DealMapper;
import dev.awn.datawarehouse.core.deal.repository.DealRepository;
import dev.awn.datawarehouse.core.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Service
public class DealServiceImpl implements DealService {
    private final DealRepository dealRepository;
    private final DealMapper dealMapper;

    @Override
    public DealDTO persistDeal(DealDTO dealDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new MissingBodyFieldsException(bindingResult);
        }

        String dealId = dealDTO.getId();
        if(dealId != null) {
            boolean dealExists = dealRepository.existsById(dealId);

            if(dealExists) {
                throw new DuplicateDealException();
            }
        }

        // Ignoring the ID in the mapping process, to ensure consistent IDs for DB vendor
        Deal deal = dealMapper.toDocument(dealDTO);

        Deal savedDeal = dealRepository.save(deal);

        return dealMapper.toDTO(savedDeal);
    }
}
