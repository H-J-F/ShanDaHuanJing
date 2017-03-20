package com.example.a11691.shandahuanjing;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 11691 on 2017/3/19.
 */

public class MyViewPager extends ViewPager {
    private boolean scrollable = true;
    private int color = 0;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!scrollable) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (scrollable) {
            super.scrollTo(x, y);
        }
    }

    public boolean isScrollable() {
        return scrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public void setBackgroundColor(int color) {
        this.color = color;
        super.setBackgroundColor(color);
    }

    public int getBackgroundColor() {
        return color;
    }
}
