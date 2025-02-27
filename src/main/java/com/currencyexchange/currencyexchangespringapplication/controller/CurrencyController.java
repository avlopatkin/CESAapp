package com.currencyexchange.currencyexchangespringapplication.controller;

import com.currencyexchange.currencyexchangespringapplication.dto.CurrencyCreationRequest;
import com.currencyexchange.currencyexchangespringapplication.dto.CurrencyCreationResponse;
import com.currencyexchange.currencyexchangespringapplication.dto.CurrencyDto;
import com.currencyexchange.currencyexchangespringapplication.dto.CurrencyUpdateRequest;
import com.currencyexchange.currencyexchangespringapplication.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @PostMapping
    public ResponseEntity<CurrencyCreationResponse> addCurrency(@RequestBody CurrencyCreationRequest request) {
        CurrencyCreationResponse response = currencyService.addCurrency(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Collection<CurrencyDto>> getAllCurrencies() {
        Collection<CurrencyDto> allCurrencies = currencyService.getAllCurrencies();
        return ResponseEntity.status(HttpStatus.OK).body(allCurrencies);
    }

    @GetMapping("/{code}")
    public ResponseEntity<CurrencyDto> getCurrencyByCode(@PathVariable String code) {
        CurrencyDto currency = currencyService.getCurrencyByCode(code);
        return ResponseEntity.ok(currency);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<CurrencyDto> updateCurrency(
            @PathVariable String code,
            @RequestBody CurrencyUpdateRequest updateRequest) {
        CurrencyDto updatedCurrency = currencyService.updateCurrency(code, updateRequest);
        return ResponseEntity.ok(updatedCurrency);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteCurrency(@PathVariable String code) {
        currencyService.deleteCurrency(code);
        return ResponseEntity.ok("Deleted " + code + " Currency Successfully");
    }
}
