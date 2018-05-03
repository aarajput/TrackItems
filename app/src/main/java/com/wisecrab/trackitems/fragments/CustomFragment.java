package com.wisecrab.trackitems.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class CustomFragment extends Fragment
{
    private Handler handler = new Handler();

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(getContentViewId(),container,false);
    }

    @Override
    public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        assign(savedInstanceState);
        repopulate();
    }

    protected abstract int getContentViewId();
    protected abstract void init(View view);
    protected abstract void assign(@Nullable Bundle savedInstanceState);
    protected void populate() {}
    protected void asyncPopulate() {}
    public final void repopulate(){
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
        populate();
    }
}
