package com.wisecrab.trackitems.viewholders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wisecrab.trackitems.R;
import com.wisecrab.trackitems.dataclasses.ItemData;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemViewHolder extends CustomViewHolder<ItemData> {
    @BindView(R.id.iv)  ImageView iv;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_desc) TextView tvDesc;

    public ItemViewHolder(ViewGroup parent, int layoutRes) {
        super(parent, layoutRes);
    }

    @Override
    protected void init(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    public void bind(ItemData data) {
        Picasso.with(itemView.getContext())
                .load(new File(data.getImagePath()))
                .placeholder(R.drawable.ic_item_placeholder)
                .into(iv);

        tvName.setText(data.getName());
        tvDesc.setText(data.getDescription());
    }
}
