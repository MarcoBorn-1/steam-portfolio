package com.example.steamportfolio.service;

import com.example.steamportfolio.entity.Item;
import com.example.steamportfolio.entity.enums.Category;
import com.example.steamportfolio.entity.enums.Quality;
import com.example.steamportfolio.entity.enums.Type;
import com.example.steamportfolio.repository.ItemRepository;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    public List<Item> getItemsWithPagination(int start) {
        StringBuilder urlBuilder = new StringBuilder("https://steamcommunity.com/market/search/render/?");
        urlBuilder.append("norender=").append(1);
        urlBuilder.append("&appid=").append(730);
        urlBuilder.append("&start=").append(start);
        urlBuilder.append("&count=").append(100);

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
        itemList.stream().limit(1).forEach(System.out::println);
        System.out.println(start);
        return itemList;
    }

    public void getAllItems() {
        int pagination = 0;
        ExecutorService executor = Executors.newFixedThreadPool(1);
        // TODO: change while argument to not be fixed number
        // TODO: find a way to not rollback the saving of the entire list (@Transactional?)
        while (pagination < 20500) {
            int finalPagination = pagination;
            Runnable task = () -> {
                try {
                    Thread.sleep(12_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                catch (DataIntegrityViolationException dive) {
                    dive.printStackTrace();
                }
                List<Item> itemList = getItemsWithPagination(finalPagination);
                if (itemList != null) {
                    System.out.println("Saving to database...");
                    //itemList.forEach(item -> itemRepository.save(item));
                    itemRepository.saveAll(itemList);
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
    // TODO: try replacing the for -> if to a stream
    public void addInfoToItem(Item item, JSONObject object) {
        String type = object.getJSONObject("asset_description").getString("type");
        for (Type typeEnum: Type.values()) {
            if (type.endsWith(typeEnum.toString())) {
                item.setType(typeEnum);
                type = type.replace(typeEnum.toString(), "").strip();
                break;
            }
        }

        for (Quality quality: Quality.values()) {
            if (type.endsWith(quality.toString())) {
                item.setQuality(quality);
                type = type.replace(quality.toString(), "").strip();
                break;
            }
        }

        if (type.length() == 0) {
            item.setCategory(Category.NORMAL);
        }
        else {
            for (Category category: Category.values()) {
                if (type.startsWith(category.toString())) {
                    item.setCategory(category);
                    break;
                }
            }
        }
    }
}
