package br.com.tiagohs.popmovies.ui.tools;

/**
 * Created by Tiago on 01/01/2017.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerWallpapers  extends android.support.v4.view.ViewPager {

    public ViewPagerWallpapers(Context context) {
        super(context);
    }

    public ViewPagerWallpapers(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}