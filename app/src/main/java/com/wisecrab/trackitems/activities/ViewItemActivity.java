package com.wisecrab.trackitems.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wisecrab.trackitems.R;
import com.wisecrab.trackitems.customclasses.CommonConstants;
import com.wisecrab.trackitems.dataclasses.ItemData;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewItemActivity extends CustomActivity {
    @BindView(R.id.iv) ImageView iv;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_desc) TextView tvDesc;
    @BindView(R.id.tv_cost) TextView tvCost;
    private ItemData itemData;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_view_item;
    }

    @Override
    protected void init() {
        this.showBackButton(true);
        ButterKnife.bind(this);
    }

    @Override
    protected void assign(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void asyncPopulate() {
        itemData = ItemData.load(ItemData.class,this.getIntent().getLongExtra(CommonConstants.ITEM_ID,-1));
    }

    @Override
    protected void populate() {
        this.setSupportActionbarTitle(itemData.getName());
        if (itemData!=null) {
            Picasso.with(this)
                    .load(new File(itemData.getImagePath()))
                    .placeholder(R.drawable.ic_item_placeholder)
                    .into(iv);
            tvName.setText(itemData.getName());
            tvDesc.setText(itemData.getDescription());
            tvCost.setText(String.valueOf(itemData.getCost()));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.menu_view_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Item")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        long id = itemData.getId();
                        ItemData.delete(ItemData.class,id);
                        Intent i = new Intent();
                        i.putExtra(CommonConstants.ITEM_ID,id);
                        this.setResult(RESULT_OK,i);
                        onBackPressed();
                    })
                    .setNegativeButton("No",null)
                    .show();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==29 && resultCode==RESULT_OK) {
            repopulate();
        }
    }

    @OnClick({R.id.fab_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_edit: {
                Intent i = new Intent(this,AddItemActivity.class);
                i.putExtra(CommonConstants.ITEM_ID,itemData.getId());
                this.startActivityForResult(i,29);
            }
            break;
        }
    }
}
