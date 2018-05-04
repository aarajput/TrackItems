package com.wisecrab.trackitems.dataclasses;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = ItemData.TABLE_NAME)
public class ItemData extends Model{
    public final static String TABLE_NAME = "items";
    public final static String COL_NAME = "items";
    public final static String COL_DESCRIPTION = "description";
    public final static String COL_IMAGE_PATH = "imagePath";
    public final static String COL_LOCATION = "location";
    public final static String COL_COST = "cost";
    public final static String COL_IS_SYNC = "isSync";

    @Column(name = COL_NAME, notNull = true)
    private String name;

    @Column(name = COL_DESCRIPTION, notNull = true)
    private String description;

    @Column(name = COL_IMAGE_PATH, notNull = true)
    private String imagePath;

    @Column(name = COL_LOCATION, notNull = true)
    private String location;

    @Column(name = COL_COST, notNull = true)
    private Float  cost;

    @Column(name = COL_IS_SYNC, notNull = true)
    private boolean  isSync;

    public String getName() {
        return name;
    }

    public ItemData setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ItemData setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public boolean isSync() {
        return isSync;
    }

    public ItemData setSync(boolean sync) {
        isSync = sync;
        return this;
    }
}
