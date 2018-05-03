package com.wisecrab.trackitems.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.activeandroid.query.Select;
import com.wisecrab.trackitems.R;
import com.wisecrab.trackitems.adapters.ItemsRecyclerAdapter;
import com.wisecrab.trackitems.customclasses.CommonConstants;
import com.wisecrab.trackitems.dataclasses.ItemData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends CustomActivity {

    @BindView(R.id.rv)  RecyclerView rv;
    private ItemsRecyclerAdapter adb;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        this.setSupportActionbarTitle(R.string.main_activity_title);
        ButterKnife.bind(this);
        adb = new ItemsRecyclerAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adb);
    }

    @Override
    protected void assign(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void asyncPopulate() {
        adb.getList().addAll(new Select().from(ItemData.class).execute());
    }

    @Override
    protected void populate() {
        adb.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==29 && resultCode==RESULT_OK) {
            long id = data.getLongExtra(CommonConstants.ITEM_ID,-1);
            if (id!=-1) {
                ItemData item = ItemData.load(ItemData.class,id);
                adb.getList().add(item);
                adb.notifyItemInserted(adb.getList().size()-1);
                rv.scrollToPosition(adb.getList().size()-1);
            }
        }
    }

    @OnClick({R.id.fab_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add: {
                Intent i = new Intent(this,AddItemActivity.class);
                this.startActivityForResult(i,29);
            }
                break;
        }
    }

}
