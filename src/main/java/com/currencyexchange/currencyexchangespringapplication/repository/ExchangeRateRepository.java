package com.currencyexchange.currencyexchangespringapplication.repository;

import com.currencyexchange.currencyexchangespringapplication.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findByBaseCurrencyCodeAndTargetCurrencyCode(String baseCurrencyCode, String targetCurrencyCode);

    Optional<ExchangeRate> findByTargetCurrencyCodeAndBaseCurrencyCode(String targetCurrencyCode, String baseCurrencyCode);
}