package com.example.steamportfolio.entity.dto;

import com.example.steamportfolio.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class MarketplaceItem {
    private int listings;
    private BigDecimal currentPrice;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
