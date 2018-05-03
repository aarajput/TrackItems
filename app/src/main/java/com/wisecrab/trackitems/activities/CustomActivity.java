package com.wisecrab.trackitems.activities;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wisecrab.trackitems.R;

public abstract class CustomActivity extends AppCompatActivity {
    private static final String TAG = "CustomActivity";
    private Handler handler = new Handler();
    private Toolbar toolbar;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setOrientation())
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setContentView(this.getContentViewId());

        View tb = this.findViewById(R.id.tb);
        if (tb != null && tb instanceof Toolbar) {
            toolbar = (Toolbar) tb;
            this.setSupportActionBar((Toolbar) tb);
            //noinspection ConstantConditions
            this.getSupportActionBar().setTitle(null);
        }

        init();
        assign(savedInstanceState);
        repopulate();
    }

    protected boolean setOrientation() {
        return true;
    }

    protected final void showBackButton(boolean show) {
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(show);
            this.getSupportActionBar().setDisplayShowHomeEnabled(show);
        }
    }

    protected abstract int getContentViewId();

    protected abstract void init();

    protected abstract void assign(@Nullable Bundle savedInstanceState);

    @WorkerThread
    protected void asyncPopulate() {
    }

    @UiThread
    protected void populate() {
    }

    protected void preload() {
    }

    public final void repopulate() {
        preload();
        new Thread(new Runnable() {
            @Override
            public void run() {
                asyncPopulate();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        populate();
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public final void setSupportActionbarTitle(CharSequence title) {
        if (this.getSupportActionBar() != null) {
            View tbTitle = toolbar.findViewById(R.id.tb_title);
            if (tbTitle != null && tbTitle instanceof TextView) {
                ((TextView) tbTitle).setText(title);
            } else
                this.getSupportActionBar().setTitle(title);
        }
    }


    public final void setSupportActionbarTitle(@StringRes int stringRes) {
        if (this.getSupportActionBar() != null)
            this.getSupportActionBar().setTitle(this.getString(stringRes));
    }

    public final LocalBroadcastManager getLocalBroadcastManager() {
        return LocalBroadcastManager.getInstance(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void startActivities(Intent[] intents) {
        super.startActivities(intents);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
