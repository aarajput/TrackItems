package com.wisecrab.trackitems.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.wisecrab.trackitems.dataclasses.ItemData;

import java.util.ArrayList;
import java.util.List;

public class ItemsParser extends Parser {
    public ItemsParser(String json) {
        super(json);
    }

    public List<ItemData> getItems() {
        List<ItemData> list = new ArrayList<>();
        JsonArray ja = this.getData().getAsJsonArray();
        for(JsonElement je : ja) {
            JsonObject jo = je.getAsJsonObject();
            ItemData item = new ItemData();
            item.setName(jo.get("name").getAsString());
            item.setDescription(jo.get("description").getAsString());
            item.setImagePath(jo.get("imagePath").getAsString());
            item.setLocation(jo.get("location").getAsString());
            try {
                item.setCost(jo.get("cost").getAsFloat());
            }catch (Exception e) {
                item.setCost(0f);
            }
            list.add(item);
        }

        return list;
    }
}
