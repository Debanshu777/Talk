package com.example.talk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.talk.Adapter.SlidePagerAdapter;
import com.example.talk.Adapter.ViewPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    private ViewPager pager;
    private AppBarLayout appBar;
    private com.google.android.material.tabs.TabLayout tabLayout;
    private FloatingActionButton adduser;
    private ImageView profile_dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        profile_dp=findViewById(R.id.user_profile_pic);
        appBar=findViewById(R.id.appbar);
        pager=findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout);
        adduser=findViewById(R.id.adduser);


        pager.setAdapter(new SlidePagerAdapter(getSupportFragmentManager()));


        tabLayout.setupWithViewPager(pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);
        pager.setAdapter(viewPagerAdapter);
        pager.setCurrentItem(1);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position!=0){
                    appBar.setVisibility(View.VISIBLE);
                }
                else{
                    appBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UserListActivity.class));
            }
        });
        profile_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });

    }


}
