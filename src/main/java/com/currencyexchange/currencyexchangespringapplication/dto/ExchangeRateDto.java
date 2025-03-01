package com.currencyexchange.currencyexchangespringapplication.dto;

import com.currencyexchange.currencyexchangespringapplication.entity.ExchangeRate;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRateDto {
    private Long id;
    private CurrencyDto baseCurrency;
    private CurrencyDto targetCurrency;
    private Double rate;

    public static ExchangeRateDto fromEntity(ExchangeRate er) {
        return ExchangeRateDto.builder()
                .id(er.getId())
                .baseCurrency(CurrencyDto.fromEntity(er.getBaseCurrency()))
                .targetCurrency(CurrencyDto.fromEntity(er.getTargetCurrency()))
                .rate(er.getRate())
                .build();
    }
}