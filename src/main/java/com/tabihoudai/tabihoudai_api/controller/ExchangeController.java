package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.ExchangeResponseDto;
import com.tabihoudai.tabihoudai_api.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/rate")
    public ResponseEntity<List<ExchangeResponseDto>> getExchangeRate() {
        List<ExchangeResponseDto> exchangeRateList = exchangeService.getExchangeRate();
        return ResponseEntity.ok(exchangeRateList);
    }
}

