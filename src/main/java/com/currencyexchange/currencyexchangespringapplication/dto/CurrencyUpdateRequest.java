package com.currencyexchange.currencyexchangespringapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyUpdateRequest {
    private String name;
    private String sign;
}
