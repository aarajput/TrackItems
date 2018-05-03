package com.wisecrab.trackitems.application;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
