package com.example.steamportfolio.entity;

import com.example.steamportfolio.entity.dto.PriceOverview;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ItemData {
    public ItemData(Item item, PriceOverview priceOverview, int listings) {
        this.item = item;
        this.currentPrice = priceOverview.getCurrentPrice();
        this.medianPrice = priceOverview.getMedianPrice();
        this.volume = priceOverview.getVolume();
        this.listings = listings;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal currentPrice;
    private BigDecimal medianPrice;
    private int volume;
    private int listings;

    @Column(name = "date")
    @CreatedDate
    private Date date;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private PortfolioValue portfolioValue;
}
