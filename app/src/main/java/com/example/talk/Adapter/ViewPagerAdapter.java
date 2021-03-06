package com.example.talk.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.talk.Fragment.CallFragment;
import com.example.talk.Fragment.ChatRoom;
import com.example.talk.Fragment.MapView;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int tabCount;
    private Context context;

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Map";
            case 1:
                return "Chat";
            case 2:
                return "Call";
        }
        return null;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0 :
                return new MapView();
            case 1:
                return new ChatRoom(context);
            case 2:
                return new CallFragment();
        }
        return null;
    }

    public ViewPagerAdapter(FragmentManager fm, int tabs, Context context) {
        super(fm);
        this.tabCount = tabs;
        this.context=context;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

