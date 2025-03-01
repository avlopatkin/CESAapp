package com.currencyexchange.currencyexchangespringapplication.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRateCreationRequest {
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private Double rate;
}