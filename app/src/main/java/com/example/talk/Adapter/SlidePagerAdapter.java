package com.example.talk.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.talk.Fragment.CallFragment;
import com.example.talk.Fragment.ChatRoom;
import com.example.talk.Fragment.MapView;

public class SlidePagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    public SlidePagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MapView();
            case 1:
                return new ChatRoom(context);
            case 2:
                return new CallFragment();

        }
        return new ChatRoom(context);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
