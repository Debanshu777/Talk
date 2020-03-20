package com.example.talk;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {
    private ViewPager pager;
    private com.google.android.material.tabs.TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
