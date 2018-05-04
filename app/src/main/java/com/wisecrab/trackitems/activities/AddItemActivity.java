package com.wisecrab.trackitems.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wisecrab.trackitems.R;
import com.wisecrab.trackitems.application.CustomApplication;
import com.wisecrab.trackitems.customclasses.CommonConstants;
import com.wisecrab.trackitems.dataclasses.ItemData;
import com.wisecrab.trackitems.helpers.ImageSelectorHelper;
import com.wisecrab.trackitems.jobs.UploadItemsJob;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddItemActivity extends CustomActivity implements ImageSelectorHelper.Listener {

    @BindView(R.id.iv)  ImageView iv;
    @BindView(R.id.et_name) EditText etName;
    @BindView(R.id.et_desc)  EditText etDesc;
    @BindView(R.id.et_location)  EditText etLocation;
    @BindView(R.id.et_cost)  EditText etCost;

    private ImageSelectorHelper imageSelectorHelper;

    private ItemData itemData;

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
        itemData = ItemData.load(ItemData.class,this.getIntent().getLongExtra(CommonConstants.ITEM_ID,-1));
        if (itemData==null)
            itemData = new ItemData();
    }

    @Override
    protected void assign(@Nullable Bundle savedInstanceState) {
        imageSelectorHelper.setListener(this);
    }

    @Override
    protected void populate() {
        etName.setText(itemData.getName());
        etDesc.setText(itemData.getDescription());
        etLocation.setText(itemData.getLocation());
        if (itemData.getCost()!=null)
            etCost.setText(String.valueOf(itemData.getCost()));
        if (itemData.getImagePath()!=null)
            initIv(new File(itemData.getImagePath()));
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

    private boolean isValid() {
        boolean isValid = true;

        if (etName.getText().toString().isEmpty())
        {
            etName.setError("Enter name");
            isValid = false;
        }

        if (etDesc.getText().toString().isEmpty())
        {
            etDesc.setError("Enter description");
            isValid = false;
        }

        if (etLocation.getText().toString().isEmpty())
        {
            etLocation.setError("Enter location");
            isValid = false;
        }

        if (etCost.getText().toString().isEmpty())
        {
            etCost.setError("Enter cost");
            isValid = false;
        }

        if (itemData.getImagePath()==null)
        {
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void initIv(File file)
    {
        Picasso.with(this)
                .load(file)
                .placeholder(R.drawable.ic_item_placeholder)
                .into(iv);
    }
    @OnClick({R.id.iv,R.id.fab_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv:
                imageSelectorHelper.getImage(false);
                break;
            case R.id.fab_done: {
                if (isValid()) {
                    itemData.setName(etName.getText().toString());
                    itemData.setDescription(etDesc.getText().toString());
                    itemData.setLocation(etLocation.getText().toString());
                    try {
                        itemData.setCost(Float.parseFloat(etCost.getText().toString()));
                    }catch (Exception e) {
                        etCost.setError("Invalid cost entered");
                    }
                    boolean startJob=false;
                    if (itemData.getId()!=null)
                        this.getLocalBroadcastManager().sendBroadcast(new Intent(CommonConstants.ACTION_REFRESH_ITEMS));
                    else
                        startJob = true;
                    itemData.save();
                    if (startJob)
                        ((CustomApplication)this.getApplicationContext()).getNetworkJobManager().add(new UploadItemsJob(this));
                    Intent i = new Intent();
                    i.putExtra(CommonConstants.ITEM_ID,itemData.getId());
                    this.setResult(RESULT_OK,i);
                    onBackPressed();
                }
            }
                break;
        }
    }

    @Override
    public void onImageSelected(File file) {
        itemData.setImagePath(file.getAbsolutePath());
        initIv(file);
    }

    @Override
    public void onImageRemoved() {
        new File(itemData.getImagePath()).delete();
    }
}
