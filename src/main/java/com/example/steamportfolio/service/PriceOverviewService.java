package com.example.steamportfolio.service;

import com.example.steamportfolio.entity.Currency;
import com.example.steamportfolio.entity.PriceOverview;
import com.example.steamportfolio.repository.CurrencyRepository;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class PriceOverviewService {
    @Autowired
    CurrencyRepository currencyRepository;

    public PriceOverview getPriceOverview(String currencyStr, int appID, String name) {
        Currency currency = currencyRepository.findCurrencyByNameIgnoreCaseOrSignIgnoreCase(currencyStr, currencyStr);
        if (currency == null) return null;

        StringBuilder urlBuilder = new StringBuilder("https://steamcommunity.com/market/priceoverview/?");
        urlBuilder.append("currency=").append(currency.getCode());
        urlBuilder.append("&appid=").append(appID);
        urlBuilder.append("&market_hash_name=").append(name.replace(" ", "+"));

        JSONObject jsonObject;
        try {
            URL url = new URL(urlBuilder.toString());
            String json = IOUtils.toString(url, StandardCharsets.UTF_8);
            jsonObject = new JSONObject(json);
        } catch (IOException e) {
            return null;
        }
        if (!jsonObject.getBoolean("success")) {
            return null;
        }

        PriceOverview item = new PriceOverview();

        try {
            item.setCurrency(currency);

            String volumeStr = jsonObject.getString("volume").replace(",", "");
            item.setVolume(Integer.parseInt(volumeStr));
            item.setCurrentPrice(getValue(jsonObject.getString("lowest_price"), currency));
            item.setMedianPrice(getValue(jsonObject.getString("median_price"), currency));
        }
        catch (NumberFormatException | JSONException nfe) {
            // TODO: add custom exception throws
            nfe.printStackTrace();
            return null;
        }

        return item;
    }

    private BigDecimal getValue(String value, Currency currency) throws NumberFormatException {
        return new BigDecimal(value.replace(currency.getSign(), "").replace(",", "."));
    }
}
