package com.boussaingault.maxime.moodtracker.models;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Maxime Boussaingault on 14/11/2017.
 */

public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // To make page moving vertically
        setPageTransformer(true, new VerticalPageTransformer(), 0);
    }

    // Method to intercept touch event
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final boolean interceptTouch = super.onInterceptTouchEvent(switchXY(event));
        switchXY(event);
        return interceptTouch;
    }

    // Method to handle touch screen motion events
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final boolean handleTouch = super.onTouchEvent(switchXY(event));
        switchXY(event);
        return handleTouch;
    }

    // Method to switch X and Y axis
    private MotionEvent switchXY(MotionEvent event) {
        final float x = getWidth() * event.getY() / getHeight();
        final float y = getHeight() * event.getX() / getWidth();
        event.setLocation(x, y);
        return event;
    }

    private static final class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {
            // To counteract the screen slide on X with a negative X translation
            view.setTranslationX(-1 * view.getWidth() * position);
            // To add a Y translation
            view.setTranslationY(view.getHeight() * position);
        }
    }
}
