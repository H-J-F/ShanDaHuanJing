package com.example.a11691.shandahuanjing;

/**
 * Created by 11691 on 2017/3/7.
 */

import java.util.List;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter {

    List<View> viewLists;

    public ViewPagerAdapter(List<View> lists)
    {
        viewLists = lists;
    }

    //获得size
    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    //销毁Item
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }

    //实例化Item
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        return viewLists.get(position);

    }
}
