package com.example.steamportfolio.entity.dto;

import java.math.BigDecimal;
import java.util.List;

public record PortfolioOverview(
        Long portfolioID,
        BigDecimal totalValue,
        BigDecimal totalInvested,
        int totalItems,
        BigDecimal overallChangePercent,
        BigDecimal overallChangeValue,
        BigDecimal lastChangePercent,
        BigDecimal lastChangeValue,
        List<PortfolioContent> portfolioContents
) {}
