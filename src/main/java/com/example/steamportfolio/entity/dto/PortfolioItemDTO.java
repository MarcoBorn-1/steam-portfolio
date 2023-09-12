package com.example.steamportfolio.entity.dto;

import java.math.BigDecimal;

public record PortfolioItemDTO(String name, int amount, BigDecimal pricePaid) {}
