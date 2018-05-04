package com.wisecrab.trackitems.parser;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Parser {
    private JsonElement response;
    public Parser(String json) {
        this.response = new JsonParser().parse(json);
    }

    public JsonElement getResponse() {
        return response;
    }

    public final int getResponseCode() {
        return response.getAsJsonObject().get("meta-data").getAsJsonObject().get("statusCode").getAsInt();
    }

    public final String getResponseMessage() {
        return response.getAsJsonObject().get("meta-data").getAsJsonObject().get("message").getAsString();
    }

    public final JsonElement getData() {
        return response.getAsJsonObject().get("data");
    }
}
