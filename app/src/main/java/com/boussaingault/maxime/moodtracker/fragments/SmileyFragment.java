package com.boussaingault.maxime.moodtracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.boussaingault.maxime.moodtracker.R;

/**
 * Created by Maxime Boussaingault on 14/11/2017.
 */

public class SmileyFragment extends Fragment {

    private static final String MOOD = "mood";
    private static final String BACKGROUND = "background";

    private String mMood;
    private String mBackground;

    private View mView;
    private ImageView mImageView;

    // Create a new instance of SmileyFragment, initialized to show the mood and the background color
    public static SmileyFragment newInstance(String mood, String background) {
        SmileyFragment smileyFragment = new SmileyFragment();

        // Supply mood and background as arguments
        Bundle args = new Bundle();
        args.putString(MOOD, mood);
        args.putString(BACKGROUND, background);
        smileyFragment.setArguments(args);
        return smileyFragment;
    }

    // When creating, retrieve this instance's mood and background color from its arguments
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMood = getArguments() != null ? getArguments().getString(MOOD) : "happy";
        mBackground = getArguments() != null ? getArguments().getString(BACKGROUND) : "light_sage";
    }

    // The fragment's UI show its instance mood and background color
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_smiley, container, false);
        mView.setBackgroundColor(getResources().getColor(getResources().getIdentifier(mBackground, "color", getActivity().getPackageName())));
        mImageView = mView.findViewById(R.id.main_page_fragment_image_view);
        mImageView.setImageResource(getResources().getIdentifier("smiley_" + mMood, "drawable", getActivity().getPackageName()));
        return mView;
    }
}
