package com.example.steamportfolio.entity.helper;

import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Setter
public class URLBuilder {
    private StringBuilder urlStr;
    public JSONObject getJson() {
        JSONObject jsonObject;
        try {
            URL url = new URL(urlStr.toString());
            String json = IOUtils.toString(url, StandardCharsets.UTF_8);
            jsonObject = new JSONObject(json);
        } catch (IOException e) {
            return null;
        }

        return jsonObject;
    }
}
