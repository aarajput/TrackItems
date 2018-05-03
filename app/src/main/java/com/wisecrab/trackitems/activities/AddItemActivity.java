package com.wisecrab.trackitems.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wisecrab.trackitems.R;
import com.wisecrab.trackitems.helpers.ImageSelectorHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddItemActivity extends CustomActivity implements ImageSelectorHelper.Listener {

    @BindView(R.id.iv)  ImageView iv;
    private ImageSelectorHelper imageSelectorHelper;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_item;
    }

    @Override
    protected void init() {
        this.setSupportActionbarTitle(R.string.add_item_activity_title);
        this.showBackButton(true);
        ButterKnife.bind(this);
        imageSelectorHelper = new ImageSelectorHelper(this);
    }

    @Override
    protected void assign(@Nullable Bundle savedInstanceState) {
        imageSelectorHelper.setListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageSelectorHelper.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageSelectorHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @OnClick({R.id.iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv:
                imageSelectorHelper.getImage(false);
                break;
        }
    }

    @Override
    public void onImageSelected(File file) {
        Picasso.with(this)
                .load(file)
                .into(iv);
    }

    @Override
    public void onImageRemoved() {

    }
}
