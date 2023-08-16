package com.example.steamportfolio.entity.helper;

public class PriceOverviewURLBuilder extends URLBuilder {
    public PriceOverviewURLBuilder(int currency, int appID, String name) {
        StringBuilder urlTemp = new StringBuilder("https://steamcommunity.com/market/priceoverview/?");
        urlTemp.append("currency=").append(currency);
        urlTemp.append("&appid=").append(appID);
        urlTemp.append("&market_hash_name=").append(name.replace(" ", "+"));
        setUrlStr(urlTemp);
    }
}
