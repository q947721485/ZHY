package com.zhy.zlib.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zhengzai on 16/9/12.
 */
public class MyPagerAdapter extends PagerAdapter {
    private List<View> views;

    public MyPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public int getCount() {
        return views.size();
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        ViewParent vp = views.get(position).getParent();
//        if (vp != null) {
//            ViewGroup parent = (ViewGroup) vp;
//            parent.removeView(views.get(position));
//        }
        ((ViewPager) container).addView(views.get(position),0);
        return views.get(position);
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

}
