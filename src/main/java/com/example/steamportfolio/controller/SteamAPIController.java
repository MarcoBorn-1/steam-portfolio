package com.example.steamportfolio.controller;

import com.example.steamportfolio.entity.PriceOverview;
import com.example.steamportfolio.service.SteamAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/steam")
public class SteamAPIController {
    private final SteamAPIService steamAPIService;

    @Autowired
    public SteamAPIController(SteamAPIService steamAPIService) {
        this.steamAPIService = steamAPIService;
    }

    /*
    Will default to polish zloty (PLN) as currency, and CS:GO as appID
     */
    @GetMapping("/priceOverview")
    public ResponseEntity<PriceOverview> getPriceOverview(@RequestParam(required = false, defaultValue = "PLN") String currencyCode,
                                                          @RequestParam(required = false, defaultValue = "730") int appID,
                                                          @RequestParam String name) {
        return ResponseEntity.ok(steamAPIService.getPriceOverview(currencyCode, appID, name));
    }
}
