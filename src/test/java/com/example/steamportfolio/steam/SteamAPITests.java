package com.example.steamportfolio.steam;

import com.example.steamportfolio.entity.PriceOverview;
import com.example.steamportfolio.service.SteamAPIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class SteamAPITests {

    @Autowired
    private SteamAPIService underTest;

    @BeforeEach
    void setUp() {
        underTest = new SteamAPIService();
    }

    @Test
    void noParams() {
        // Given
        String emptyName = "";
        String defaultCurrencyCode = "PLN";
        int defaultAppID = 730;

        // When
        PriceOverview result = underTest.getPriceOverview(defaultCurrencyCode, defaultAppID, emptyName);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void nonExistingName() {
        // Given
        String nonExistingName = "This item does not exist";
        String defaultCurrencyCode = "PLN";
        int defaultAppID = 730;

        // When
        PriceOverview result = underTest.getPriceOverview(defaultCurrencyCode, defaultAppID, nonExistingName);

        // Then
        assertThat(result).isNull();
    }

    /***
     The item in question was not listed on the Steam Market for a long time, and even if it did, it'd be instantly bought.
     That means that although the API will return success: true, it won't return any information in the JSON.
     */
    @Test
    void noDataForGivenName() {
        // Given
        String noDataForName = "Sticker | iBUYPOWER (Holo) | Katowice 2014";
        String defaultCurrencyCode = "PLN";
        int defaultAppID = 730;

        // When
        PriceOverview result = underTest.getPriceOverview(defaultCurrencyCode, defaultAppID, noDataForName);

        // Then
        assertThat(result).isNull();
    }
}
