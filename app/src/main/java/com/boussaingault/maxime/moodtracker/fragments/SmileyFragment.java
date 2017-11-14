package com.boussaingault.maxime.moodtracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.boussaingault.maxime.moodtracker.R;

public class SmileyFragment extends Fragment {

    private View mView;
    private ImageView mImageView;
    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        position = bundle.getInt("current_page", 3);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_smiley, container, false);
        mImageView = (ImageView) mView.findViewById(R.id.main_page_fragment_image_view);

        switch (position) {
            case 0:
                setImageView("sad", "faded_red" );
                return mView;
            case 1:
                setImageView("disappointed", "warm_grey" );
                return mView;
            case 2:
                setImageView("normal", "cornflower_blue_65" );
                return mView;
            case 3:
                setImageView("happy", "light_sage");
                return mView;
            case 4:
                setImageView("super_happy", "banana_yellow" );
                return mView;
            default:
                return null;
        }
    }

    private void setImageView(String smiley, String color) {
        int image = getResources().getIdentifier("smiley_" + smiley, "drawable", getActivity().getPackageName());
        int id = getResources().getIdentifier("smiley_" + smiley, "id", getActivity().getPackageName());
        int background = getResources().getIdentifier(color, "color", getActivity().getPackageName());

        mImageView.setImageResource(image);
        mImageView.setId(id);
        mView.setBackgroundColor(getResources().getColor(background));
    }
}
