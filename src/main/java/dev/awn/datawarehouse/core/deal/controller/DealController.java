package dev.awn.datawarehouse.core.deal.controller;

import dev.awn.datawarehouse.core.deal.dto.DealDTO;
import dev.awn.datawarehouse.core.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/deals")
@RequiredArgsConstructor
public class DealController {
    private final Logger logger = LoggerFactory.getLogger(DealController.class);
    private final DealService dealService;

    @PostMapping
    public ResponseEntity<DealDTO> persistDeal(@Validated @RequestBody DealDTO dealDTO,
                                               BindingResult bindingResult) {
        DealDTO savedDealDTO = dealService.persistDeal(dealDTO, bindingResult);

        return new ResponseEntity<>(savedDealDTO, HttpStatus.OK);
    }

}
