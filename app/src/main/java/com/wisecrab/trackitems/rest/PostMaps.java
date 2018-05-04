package com.wisecrab.trackitems.rest;

import com.wisecrab.trackitems.dataclasses.ItemData;

import java.util.HashMap;

public class PostMaps {
    public static HashMap<String,String> postItem(ItemData item) {
        HashMap<String,String> map = new HashMap<>();
        map.put("name",item.getName());
        map.put("description",item.getDescription());
        map.put("location",item.getLocation());
        map.put("cost",String.valueOf(item.getCost()));
        map.put("imagePath",item.getImagePath());
        return map;
    }
}
