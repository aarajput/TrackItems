package com.wisecrab.trackitems.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.wisecrab.trackitems.R;
import com.wisecrab.trackitems.adapters.ItemsRecyclerAdapter;
import com.wisecrab.trackitems.customclasses.CommonConstants;
import com.wisecrab.trackitems.dataclasses.ItemData;
import com.wisecrab.trackitems.parser.ItemsParser;
import com.wisecrab.trackitems.postboy.PostBoy;
import com.wisecrab.trackitems.postboy.PostBoyException;
import com.wisecrab.trackitems.postboy.PostBoyListener;
import com.wisecrab.trackitems.postboy.RequestType;
import com.wisecrab.trackitems.rest.WebUrls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends CustomActivity implements PostBoyListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.rv)
    RecyclerView rv;
    private ItemsRecyclerAdapter adb;

    private Receiver receiver = new Receiver();
    private PostBoy pb;

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

        pb = new PostBoy.Builder(this, RequestType.GET, WebUrls.BASE_URL+WebUrls.ITEMS).create();
    }

    @Override
    protected void assign(@Nullable Bundle savedInstanceState) {
        this.getLocalBroadcastManager().registerReceiver(receiver, new IntentFilter(CommonConstants.ACTION_REFRESH_ITEMS));
        pb.setListener(this);
    }

    @Override
    protected void asyncPopulate() {
        adb.getList().clear();
        adb.getList().addAll(new Select().from(ItemData.class).execute());
    }

    @Override
    protected void populate() {
        if (adb.getList().size()==0)
            pb.call();
        else
            adb.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.getLocalBroadcastManager().unregisterReceiver(receiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 29 && resultCode == RESULT_OK) {
            long id = data.getLongExtra(CommonConstants.ITEM_ID, -1);
            if (id != -1) {
                ItemData item = ItemData.load(ItemData.class, id);
                adb.getList().add(item);
                adb.notifyItemInserted(adb.getList().size() - 1);
                rv.scrollToPosition(adb.getList().size() - 1);
            }
        } else if (requestCode == 30 && resultCode == RESULT_OK) {
            long id = data.getLongExtra(CommonConstants.ITEM_ID, -1);
            if (id != -1) {
                int index = adb.getList().indexOf(getItemById(id));
                adb.getList().remove(index);
                adb.notifyItemRemoved(index);
            }
        }
    }

    private ItemData getItemById(long id) {
        for (ItemData i : adb.getList()) {
            if (id == i.getId())
                return i;
        }
        return null;
    }

    @OnClick({R.id.fab_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add: {
                Intent i = new Intent(this, AddItemActivity.class);
                this.startActivityForResult(i, 29);
            }
            break;
        }
    }

    @Override
    public void onPostBoyConnecting() throws PostBoyException {

    }

    @Override
    public void onPostBoyAsyncConnected(String json, int responseCode) throws PostBoyException {
        ItemsParser parser = new ItemsParser(json);
        if (parser.getResponseCode()==200) {
            ActiveAndroid.beginTransaction();
            try {
                List<ItemData> items =  parser.getItems();
                for(ItemData item : items) {
                    item.setSync(true);
                    item.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            }finally {
                ActiveAndroid.endTransaction();
            }
        }

        adb.getList().clear();
        adb.getList().addAll(new Select().from(ItemData.class).execute());
    }

    @Override
    public void onPostBoyConnected(String json, int responseCode) throws PostBoyException {
        adb.notifyDataSetChanged();
    }

    @Override
    public void onPostBoyConnectionFailure() throws PostBoyException {

    }

    @Override
    public void onPostBoyError(PostBoyException e) {
        Log.e(TAG, "onPostBoyError: " + e.toString());
    }

    private class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            repopulate();
        }
    }

}
