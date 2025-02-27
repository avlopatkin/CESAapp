package com.currencyexchange.currencyexchangespringapplication.service;

import com.currencyexchange.currencyexchangespringapplication.dto.CurrencyCreationRequest;
import com.currencyexchange.currencyexchangespringapplication.dto.CurrencyCreationResponse;
import com.currencyexchange.currencyexchangespringapplication.dto.CurrencyDto;
import com.currencyexchange.currencyexchangespringapplication.dto.CurrencyUpdateRequest;
import com.currencyexchange.currencyexchangespringapplication.entity.Currency;
import com.currencyexchange.currencyexchangespringapplication.repository.CurrencyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z]{3}$");

    @Transactional
    public CurrencyDto updateCurrency(String code, CurrencyUpdateRequest updateRequest) {
        validateCurrencyCode(code);
        Currency currency = currencyRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found"));
        if (updateRequest.getName() != null) {
            currency.setFullName(updateRequest.getName());
        }
        if (updateRequest.getSign() != null) {
            currency.setSign(updateRequest.getSign());
        }
        currencyRepository.save(currency);
        return CurrencyDto.fromEntity(currency);
    }

    @Transactional
    public CurrencyCreationResponse addCurrency(CurrencyCreationRequest request) {
        validateCurrencyCode(request.getCode());
        validateCurrencyName(request.getFullName());
        validateCurrencySign(request.getSign());

        boolean isCurrencyExists = currencyRepository.findByCode(request.getCode()).isPresent();
        if (isCurrencyExists) {
            throw new IllegalArgumentException("Currency with code " + request.getCode() + " already exists");
        }
        Currency currency = new Currency(request.getCode(), request.getFullName(), request.getSign());
        Currency saved = currencyRepository.save(currency);
        return new CurrencyCreationResponse(
                saved.getId(),
                saved.getCode(),
                saved.getFullName(),
                saved.getSign()
        );
    }

    public Collection<CurrencyDto> getAllCurrencies() {
        return currencyRepository.findAll()
                .stream()
                .map(CurrencyDto::fromEntity)
                .collect(Collectors.toList());
    }

    public CurrencyDto getCurrencyByCode(String code) {
        validateCurrencyCode(code);
        Currency currency = currencyRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Currency is not found"));
        return CurrencyDto.fromEntity(currency);
    }

    @Transactional
    public void deleteCurrency(String code) {
        validateCurrencyCode(code);
        Currency currency = currencyRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Currency is not found"));
        currencyRepository.delete(currency);
    }

    private void validateCurrencyCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Currency code cannot be null or empty");
        }
        if (!CODE_PATTERN.matcher(code).matches()) {
            throw new IllegalArgumentException("Currency code must consist of 3 uppercase letters");
        }
    }

    private void validateCurrencyName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Currency name cannot be null or empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Currency name cannot exceed 100 characters");
        }
    }

    private void validateCurrencySign(String sign) {
        if (sign == null || sign.isBlank()) {
            throw new IllegalArgumentException("Currency sign cannot be null or empty");
        }
        if (sign.length() > 3) {
            throw new IllegalArgumentException("Currency sign cannot exceed 3 characters");
        }
    }
}
