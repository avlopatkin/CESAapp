package com.currencyexchange.currencyexchangespringapplication.service;

import com.currencyexchange.currencyexchangespringapplication.dto.*;
import com.currencyexchange.currencyexchangespringapplication.entity.Currency;
import com.currencyexchange.currencyexchangespringapplication.entity.ExchangeRate;
import com.currencyexchange.currencyexchangespringapplication.repository.CurrencyRepository;
import com.currencyexchange.currencyexchangespringapplication.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    public List<ExchangeRateDto> getAllExchangeRates() {
        return exchangeRateRepository.findAll().stream()
                .map(ExchangeRateDto::fromEntity)
                .toList();
    }

    public ExchangeRateDto getExchangeRate(String currencyPair) {
        String[] parsed = parseCurrencyPair(currencyPair);
        String base = parsed[0];
        String target = parsed[1];

        ExchangeRate exchangeRate = exchangeRateRepository
                .findByBaseCurrencyCodeAndTargetCurrencyCode(base, target)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Exchange rate not found for pair: " + currencyPair
                ));

        return ExchangeRateDto.fromEntity(exchangeRate);
    }

    @Transactional
    public ExchangeRateDto addExchangeRate(ExchangeRateCreationRequest request) {
        Currency baseCurrency = currencyRepository.findByCode(request.getBaseCurrencyCode())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Base currency not found: " + request.getBaseCurrencyCode()
                ));

        Currency targetCurrency = currencyRepository.findByCode(request.getTargetCurrencyCode())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Target currency not found: " + request.getTargetCurrencyCode()
                ));

//        лучше использовать(добавить) index и обработать исключение
        boolean exists = exchangeRateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(
                request.getBaseCurrencyCode(),
                request.getTargetCurrencyCode()
        ).isPresent();

        if (exists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Exchange rate already exists for pair: "
                            + request.getBaseCurrencyCode() + request.getTargetCurrencyCode()
            );
        }

        ExchangeRate newRate = ExchangeRate.builder()
                .baseCurrency(baseCurrency)
                .targetCurrency(targetCurrency)
                .rate(request.getRate())
                .build();

        exchangeRateRepository.save(newRate);
        return ExchangeRateDto.fromEntity(newRate);
    }

    @Transactional
    public ExchangeRateDto updateExchangeRate(String currencyPair, double newRate) {
        String[] parsed = parseCurrencyPair(currencyPair);
        String base = parsed[0];
        String target = parsed[1];

        ExchangeRate exchangeRate = exchangeRateRepository
                .findByBaseCurrencyCodeAndTargetCurrencyCode(base, target)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Exchange rate not found for pair: " + currencyPair
                ));

        exchangeRate.setRate(newRate);
        exchangeRateRepository.save(exchangeRate);

        return ExchangeRateDto.fromEntity(exchangeRate);
    }

    @Transactional
    public ExchangeResponseDto exchange(String from, String to, double amount) {
        double rate = findRate(from, to);

        double converted = amount * rate;

        Currency baseCurrency = currencyRepository.findByCode(from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Base currency not found: " + from
                ));

        Currency targetCurrency = currencyRepository.findByCode(to)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Target currency not found: " + to
                ));

        return ExchangeResponseDto.builder()
                .baseCurrency(CurrencyDto.fromEntity(baseCurrency))
                .targetCurrency(CurrencyDto.fromEntity(targetCurrency))
                .rate(rate)
                .amount(amount)
                .convertedAmount(converted)
                .build();
    }

    private String[] parseCurrencyPair(String currencyPair) {
        if (currencyPair == null || currencyPair.length() < 6) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid currency pair: " + currencyPair
            );
        }
        String base = currencyPair.substring(0, 3).toUpperCase();
        String target = currencyPair.substring(3).toUpperCase();
        return new String[]{base, target};
    }

    private double findRate(String from, String to) {
        // прямой
        Optional<ExchangeRate> direct = exchangeRateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(from, to);
        if (direct.isPresent()) {
            return direct.get().getRate();
        }

        // обратный
        Optional<ExchangeRate> reverse = exchangeRateRepository.findByTargetCurrencyCodeAndBaseCurrencyCode(from, to);
        if (reverse.isPresent()) {
            return 1.0 / reverse.get().getRate();
        }

        // через USD
        double fromToUsd = getRateForPair(from, "USD");
        double usdToTarget = getRateForPair("USD", to);

        return fromToUsd * usdToTarget;
    }

    private double getRateForPair(String base, String target) {
        // прямой
        Optional<ExchangeRate> direct = exchangeRateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(base, target);
        if (direct.isPresent()) {
            return direct.get().getRate();
        }

        // обратный
        Optional<ExchangeRate> reverse = exchangeRateRepository.findByTargetCurrencyCodeAndBaseCurrencyCode(base, target);
        if (reverse.isPresent()) {
            return 1.0 / reverse.get().getRate();
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No exchange rate found for " + base + " -> " + target
        );
    }
}
