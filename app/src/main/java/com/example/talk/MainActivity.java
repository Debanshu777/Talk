package com.example.talk;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.talk.Adapter.SlidePagerAdapter;
import com.example.talk.Adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {
    private ViewPager pager;
    private com.google.android.material.tabs.TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        pager=findViewById(R.id.pager);
        pager.setAdapter(new SlidePagerAdapter(getSupportFragmentManager()));
        tabLayout = findViewById(R.id.tab_layout);

        tabLayout.setupWithViewPager(pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);
        pager.setAdapter(viewPagerAdapter);
        pager.setCurrentItem(1);
    }
}
