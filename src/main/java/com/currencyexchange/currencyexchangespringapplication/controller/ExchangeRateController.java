package com.currencyexchange.currencyexchangespringapplication.controller;

import com.currencyexchange.currencyexchangespringapplication.dto.*;
import com.currencyexchange.currencyexchangespringapplication.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchangeRates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping
    public ResponseEntity<List<ExchangeRateDto>> getAllExchangeRates() {
        List<ExchangeRateDto> rates = exchangeRateService.getAllExchangeRates();
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/{currencyPair}")
    public ResponseEntity<ExchangeRateDto> getExchangeRate(@PathVariable String currencyPair) {
        ExchangeRateDto dto = exchangeRateService.getExchangeRate(currencyPair);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ExchangeRateDto> addExchangeRate(
            @RequestParam String baseCurrencyCode,
            @RequestParam String targetCurrencyCode,
            @RequestParam Double rate
    ) {
        ExchangeRateCreationRequest request = ExchangeRateCreationRequest.builder()
                .baseCurrencyCode(baseCurrencyCode)
                .targetCurrencyCode(targetCurrencyCode)
                .rate(rate)
                .build();

        ExchangeRateDto created = exchangeRateService.addExchangeRate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{currencyPair}")
    public ResponseEntity<ExchangeRateDto> updateExchangeRate(
            @PathVariable String currencyPair,
            @RequestParam Double rate
    ) {
        ExchangeRateDto updated = exchangeRateService.updateExchangeRate(currencyPair, rate);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/exchange")
    public ResponseEntity<ExchangeResponseDto> exchange(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount
    ) {
        ExchangeResponseDto result = exchangeRateService.exchange(from, to, amount);
        return ResponseEntity.ok(result);
    }
}