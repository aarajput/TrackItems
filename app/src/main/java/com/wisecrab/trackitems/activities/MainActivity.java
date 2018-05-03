package com.wisecrab.trackitems.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wisecrab.trackitems.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends CustomActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        this.setSupportActionbarTitle(R.string.main_activity_title);
        ButterKnife.bind(this);
    }

    @Override
    protected void assign(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.fab_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add: {
                Intent i = new Intent(this,AddItemActivity.class);
                this.startActivity(i);
            }
                break;
        }
    }

}
