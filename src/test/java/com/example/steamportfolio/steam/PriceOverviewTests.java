package com.example.steamportfolio.steam;

import com.example.steamportfolio.entity.dto.PriceOverview;
import com.example.steamportfolio.service.PriceOverviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PriceOverviewTests {

    @Autowired
    private PriceOverviewService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PriceOverviewService();
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

    /*
     The item in question was not listed on the Steam Market for a long time, and even if it did, it'd be instantly bought.
     That means that although the API will return success: true, it won't return any information in the JSON.
     If only this test fails (shouldn't really happen), check the result manually, just in case.
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

    @Test
    void invalidCurrencyCode() {
        // Given
        String name = "Spectrum 2 Case";
        String invalidCurrencyCode = "Bananas";
        int defaultAppID = 730;

        // When
        PriceOverview result = underTest.getPriceOverview(invalidCurrencyCode, defaultAppID, name);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void invalidAppId() {
        // Given
        String name = "Spectrum 2 Case";
        String validCurrencyCode = "PLN";
        int invalidAppID = -1;

        // When
        PriceOverview result = underTest.getPriceOverview(validCurrencyCode, invalidAppID, name);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void validRequest() {
        // Given
        String name = "Spectrum 2 Case";
        String validCurrencyCode = "PLN";
        int defaultAppID = 730;

        // When
        PriceOverview result = underTest.getPriceOverview(validCurrencyCode, defaultAppID, name);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCurrency().getCode()).isEqualTo(6);
        assertThat(result.getCurrentPrice()).isPositive();
        assertThat(result.getMedianPrice()).isPositive();
        assertThat(result.getVolume()).isPositive();
    }
}
