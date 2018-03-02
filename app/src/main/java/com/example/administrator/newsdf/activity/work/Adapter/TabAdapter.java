package com.example.administrator.newsdf.activity.work.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class TabAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fm;
    public static ArrayList<String> ids;
    public static ArrayList<String> mData;
    public static String wbeID;

    public TabAdapter(FragmentManager fm, ArrayList<String> name) {
        super(fm);
        this.mData = name;
        ids = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int arg0) {
        TabFragment fragment = new TabFragment(arg0);
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

    public void getAdate(ArrayList<String> ids, String wbsid) {
        this.ids = ids;
        this.wbeID = wbsid;
        notifyDataSetChanged();
    }

    public void getData(ArrayList<String> mData) {
        this.mData = mData;

        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
