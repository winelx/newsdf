package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.administrator.newsdf.Bean.Audio;
import com.example.administrator.newsdf.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class PopAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Audio> list;
    private LayoutInflater inflater;

    public PopAdapter(ArrayList<Audio> list, Context context) {
        super();
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_pop, null);
        }
        TextView tv_item = (TextView) convertView.findViewById(R.id.tv_item);
        tv_item.setText(list.get(position).getName());
        Log.i("olgodse", list.get(position).getName());

        return convertView;
    }


}