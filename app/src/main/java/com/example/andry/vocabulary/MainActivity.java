package com.example.andry.vocabulary;

import android.content.pm.ActivityInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        List<MainTab> mainTabs = new ArrayList<>();
        mainTabs.add(LearnTab.newInstance());
        mainTabs.add(LearnedTab.newInstance());
        TabsPagerAdapter mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), mainTabs);

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mTabsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private class TabsPagerAdapter extends FragmentPagerAdapter {

        private List<MainTab> mMainTabs;

        TabsPagerAdapter(FragmentManager fm, List<MainTab> mainTabs) {
            super(fm);
            mMainTabs = mainTabs;
        }

        @Override
        public Fragment getItem(int i) {
            return mMainTabs.get(i);
        }

        @Override
        public int getCount() {
            return mMainTabs.size();
        }
    }
}
