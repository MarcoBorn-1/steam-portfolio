package com.example.steamportfolio.entity.dto;

import java.math.BigDecimal;

public record PortfolioContent(int amountOwned,
                               BigDecimal pricePaid,
                               BigDecimal currentPrice,
                               BigDecimal medianPrice,
                               BigDecimal totalValue,
                               int volume,
                               int listings) {
}
