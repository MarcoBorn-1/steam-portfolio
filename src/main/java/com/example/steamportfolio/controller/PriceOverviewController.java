package com.example.steamportfolio.controller;

import com.example.steamportfolio.entity.PriceOverview;
import com.example.steamportfolio.service.PriceOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/steam")
public class PriceOverviewController {
    private final PriceOverviewService priceOverviewService;

    @Autowired
    public PriceOverviewController(PriceOverviewService priceOverviewService) {
        this.priceOverviewService = priceOverviewService;
    }

    /*
    Will default to dollar (USD) as currency, and CS:GO (730) as appID
    */
    @GetMapping("/priceOverview")
    public ResponseEntity<PriceOverview> getPriceOverview(@RequestParam(required = false, defaultValue = "USD") String currency,
                                                          @RequestParam(required = false, defaultValue = "730") int appID,
                                                          @RequestParam String name) {
        return ResponseEntity.ok(priceOverviewService.getPriceOverview(currency, appID, name));
    }
}
