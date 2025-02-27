package com.currencyexchange.currencyexchangespringapplication.dto;

import com.currencyexchange.currencyexchangespringapplication.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {
    private long id;
    private String code;
    private String fullName;
    private String sign;

    public static CurrencyDto fromEntity(Currency currency) {
        return new CurrencyDto(
                currency.getId(),
                currency.getCode(),
                currency.getFullName(),
                currency.getSign()
        );
    }
}
