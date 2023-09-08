package com.example.steamportfolio.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PriceOverview {
    private BigDecimal currentPrice;
    private BigDecimal medianPrice;
    private Currency currency;
    private int volume;
}
