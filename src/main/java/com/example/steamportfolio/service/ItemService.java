package com.example.steamportfolio.service;

import com.example.steamportfolio.entity.Item;
import com.example.steamportfolio.entity.enums.Category;
import com.example.steamportfolio.entity.enums.Quality;
import com.example.steamportfolio.entity.enums.Type;
import com.example.steamportfolio.entity.helper.MarketplaceURLBuilder;
import com.example.steamportfolio.repository.ItemRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public int getItemListingAmount(String name) {
        MarketplaceURLBuilder urlBuilder = new MarketplaceURLBuilder(1, name);
        JSONObject jsonObject = urlBuilder.getJson();
        if (jsonObject == null || !jsonObject.getBoolean("success")) return -1;

        JSONArray array = jsonObject.getJSONArray("results");
        if (array.isEmpty()) return -1;
        JSONObject object = array.getJSONObject(0);

        return object.getInt("sell_listings");
    }

    public List<Item> getItemsWithPagination(int start) {
        MarketplaceURLBuilder urlBuilder = new MarketplaceURLBuilder(start);
        JSONObject jsonObject = urlBuilder.getJson();
        if (jsonObject == null || !jsonObject.getBoolean("success")) return null;

        JSONArray array = jsonObject.getJSONArray("results");
        if (array.isEmpty()) return null;
        List<Item> itemList = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            Item item = new Item();
            JSONObject obj = array.getJSONObject(i);
            item.setName(obj.getString("name"));
            item.setPictureURL(
                    "https://steamcommunity-a.akamaihd.net/economy/image/" +
                            obj.getJSONObject("asset_description").getString("icon_url") +
                            "/164fx164f/"
            );
            item.setCommodity(obj.getJSONObject("asset_description").getInt("commodity") == 1);
            addInfoToItem(item, obj);

            itemList.add(item);
        }
        System.out.println("Scanned " + start + " items.");
        return itemList;
    }

    public void getAllItems() {
        int pagination = 0;
        int total_count = getItemQuantityFromMarketplace();
        if (total_count < 0) return;

        // ExecutorService used to execute and queue up tasks to retrieve market items
        // Only one thread pool is used to make sure the rate limit for requests is not met
        ExecutorService executor = Executors.newFixedThreadPool(1);

        // An additional 100 is added to the total amount, because the amount of items can fluctuate by +/- 50 items.
        while (pagination < total_count + 100) {
            int finalPagination = pagination; // Runnable task below requires "effectively final" variable

            Runnable task = () -> {
                try {
                    Thread.sleep(12_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                List<Item> itemList = getItemsWithPagination(finalPagination);
                if (itemList != null) {
                    System.out.println("Saving to database...");
                    itemList.forEach(item -> {
                        try {
                            itemRepository.save(item);
                        }
                        catch (DataIntegrityViolationException e) {
                            // If item violates unique name constraint, it will not save
                        }
                    });
                }
                else {
                    System.out.println("Nothing to save...");
                }
            };
            executor.execute(task);
            pagination += 100;
        }
    }

    // Adds category, quality and type to item using JSONObject
    private void addInfoToItem(Item item, JSONObject object) {
        String info = object.getJSONObject("asset_description").getString("type");
        for (Type type: Type.values()) {
            if (info.endsWith(type.toString())) {
                item.setType(type);
                info = info.replace(type.toString(), "").strip();
                break;
            }
        }

        for (Quality quality: Quality.values()) {
            if (info.endsWith(quality.toString())) {
                item.setQuality(quality);
                info = info.replace(quality.toString(), "").strip();
                break;
            }
        }

        if (info.length() == 0) {
            item.setCategory(Category.NORMAL);
        }
        else {
            for (Category category: Category.values()) {
                if (info.startsWith(category.toString())) {
                    item.setCategory(category);
                    break;
                }
            }
        }
    }

    // Retrieves item quantity from marketplace.
    private int getItemQuantityFromMarketplace() {
        MarketplaceURLBuilder urlBuilder = new MarketplaceURLBuilder(0);
        JSONObject jsonObject = urlBuilder.getJson();
        if (jsonObject == null || !jsonObject.getBoolean("success")) return -1;

        return jsonObject.getInt("total_count");
    }
}
