package com.boussaingault.maxime.moodtracker.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.boussaingault.maxime.moodtracker.R;
import com.boussaingault.maxime.moodtracker.fragments.SmileyFragment;
import com.boussaingault.maxime.moodtracker.models.VerticalViewPager;

public class MainActivity extends FragmentActivity {

    private VerticalViewPager mVerticalViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVerticalViewPager = findViewById(R.id.activity_main_view_pager);
        mVerticalViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mVerticalViewPager.setCurrentItem(3);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            SmileyFragment smileyFragment = new SmileyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("current_page", position);
            smileyFragment.setArguments(bundle);
            return smileyFragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity::onDestroy()");
    }
}
