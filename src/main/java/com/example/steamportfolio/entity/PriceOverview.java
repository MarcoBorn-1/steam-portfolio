package com.example.steamportfolio.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PriceOverview {
    private BigDecimal currentPrice;
    private BigDecimal medianPrice;
    private Currency currency;
    private int volume;
}
