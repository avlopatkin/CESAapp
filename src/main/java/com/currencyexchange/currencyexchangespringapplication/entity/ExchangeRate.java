package com.currencyexchange.currencyexchangespringapplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exchange_rates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "base_currency_id")
    private Currency baseCurrency;  // USD

    @ManyToOne(optional = false)
    @JoinColumn(name = "target_currency_id")
    private Currency targetCurrency; // RUB

    @Column(nullable = false)
    private Double rate; // Коэффициент, например 73.50
}