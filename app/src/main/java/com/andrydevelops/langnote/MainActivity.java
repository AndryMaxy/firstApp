package com.andrydevelops.langnote;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.andry.www.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, getString(R.string.app_ad_id));

        AdView adView = findViewById(R.id.main_ad);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

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
