package com.wisecrab.trackitems.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wisecrab.trackitems.R;
import com.wisecrab.trackitems.dataclasses.ItemData;
import com.wisecrab.trackitems.viewholders.ItemViewHolder;

import java.util.ArrayList;

public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private ArrayList<ItemData> list = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent, R.layout.vh_item);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public ArrayList<ItemData> getList() {
        return list;
    }
}
