package com.currencyexchange.currencyexchangespringapplication.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeResponseDto {
    private CurrencyDto baseCurrency;
    private CurrencyDto targetCurrency;
    private Double rate;
    private Double amount;
    private Double convertedAmount;
}