package com.example.administrator.newsdf.activity.work.PushAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 作者：winelx
 * 时间：2017/12/2 0002:下午 16:49
 * 说明：
 */
public class PushAdapter extends FragmentPagerAdapter {
    public static ArrayList<String> mData;
    public static String Content;

    public PushAdapter(FragmentManager fm, ArrayList<String> mData) {
        super(fm);
        this.mData = mData;
    }

    @Override
    public Fragment getItem(int arg0) {
        PushFrgment fragment = new PushFrgment(arg0);
        return fragment;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position);
    }

    public void getID(String Content) {
        this.Content = Content;

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}