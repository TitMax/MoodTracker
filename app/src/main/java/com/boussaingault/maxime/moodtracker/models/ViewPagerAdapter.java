package com.boussaingault.maxime.moodtracker.models;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.boussaingault.maxime.moodtracker.fragments.SmileyFragment;

/**
 * Created by Tit Max on 16/11/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return SmileyFragment.newInstance( "sad", "faded_red");
            case 1:
                return SmileyFragment.newInstance( "disappointed", "warm_grey");
            case 2:
                return SmileyFragment.newInstance( "normal", "cornflower_blue_65");
            case 3:
                return SmileyFragment.newInstance( "happy", "light_sage");
            case 4:
                return SmileyFragment.newInstance( "super_happy", "banana_yellow");
            default:
                return null;
        }
    }
}
