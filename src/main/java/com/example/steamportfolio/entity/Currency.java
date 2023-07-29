package com.example.steamportfolio.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum Currency {
    USD(0, "USD", "$", Position.LEFT),
    EUR(3, "EUR", "€", Position.RIGHT),
    PLN(6, "PLN", "zł", Position.RIGHT);

    private final int CURRENCY_CODE;
    private final String CURRENCY_NAME;
    private final String CURRENCY_SIGN;
    private final Position CURRENCY_POSITION;

    // Trying to find a matching currency and returning
    public static Currency findCurrency(String currency) {
        for (Currency c: Currency.values()) {
            if (Objects.equals(currency, c.CURRENCY_NAME) ||
                Objects.equals(currency, c.CURRENCY_SIGN)) {
                return c;
            }

            try {
                if (Objects.equals(Integer.parseInt(currency), c.CURRENCY_CODE)) return c;
            }
            catch (NumberFormatException e) {

            }
        }
        return null;
    }
}
