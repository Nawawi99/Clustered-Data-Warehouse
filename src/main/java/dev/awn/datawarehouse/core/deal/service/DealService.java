package dev.awn.datawarehouse.core.deal.service;

import dev.awn.datawarehouse.core.deal.dto.DealDTO;
import org.springframework.validation.BindingResult;

public interface DealService {
    DealDTO persistDeal(DealDTO dealDTO, BindingResult bindingResult);
}
