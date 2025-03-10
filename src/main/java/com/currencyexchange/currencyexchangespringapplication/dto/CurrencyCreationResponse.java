package com.currencyexchange.currencyexchangespringapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyCreationResponse {
    private long id;
    private String code;
    private String fullName;
    private String sign;
}
