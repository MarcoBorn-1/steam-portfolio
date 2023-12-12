package com.example.steamportfolio.entity.helper;

public class MarketplaceURLBuilder extends URLBuilder {
    public MarketplaceURLBuilder(int start) {
        StringBuilder urlTemp = new StringBuilder("https://steamcommunity.com/market/search/render/?");
        urlTemp.append("norender=").append(1);
        urlTemp.append("&appid=").append(730);
        urlTemp.append("&start=").append(start);
        urlTemp.append("&count=").append(100);
        setUrlStr(urlTemp);
    }

    public MarketplaceURLBuilder(int count, String query) {
        StringBuilder urlTemp = new StringBuilder("https://steamcommunity.com/market/search/render/?");
        urlTemp.append("norender=").append(1);
        urlTemp.append("&appid=").append(730);
        urlTemp.append("&count=").append(count);
        urlTemp.append("&query=").append(query.replace(" ", "+"));
        setUrlStr(urlTemp);
    }
}
