package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.newsdf.Bean.Tab_fragment_item;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Dates;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class TabAdapters extends BaseAdapter {
    private Context mContext;
    private ArrayList<Tab_fragment_item> mData;
    private Dates dates = new Dates();
    String stsus;

    public TabAdapters(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int mData) {
        return mData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_icon, parent, false);
            holder = new ViewHolder();
            holder.tab_str = (TextView) convertView.findViewById(R.id.tab_str); //标段
            holder.tab_content = (TextView) convertView.findViewById(R.id.tab_content);
            holder.tab_user = (TextView) convertView.findViewById(R.id.tab_user);
            holder.tab_time = (TextView) convertView.findViewById(R.id.tab_time);
            holder.tab_fixed_tiem = (TextView) convertView.findViewById(R.id.tab_fixed_tiem);
            holder.tab_view = (TextView) convertView.findViewById(R.id.tab_view);
            holder.tab_statu2 = (TextView) convertView.findViewById(R.id.tab_statu2);
            convertView.setTag(holder);   //将Holder存储到convertView中
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        /**
         *
         *
         * 有状态未添加
         */
        //判断是否有消息
        holder.tab_str.setText(mData.get(position).getStr());
        holder.tab_content.setText(mData.get(position).getContent());
        holder.tab_user.setText(mData.get(position).getUser());
        holder.tab_time.setText(mData.get(position).getTime());
        holder.tab_fixed_tiem.setText(mData.get(position).getTime());
        holder.tab_statu2.setText(mData.get(position).getStatus1());
        stsus = mData.get(position).getStatus1();
        switch (stsus) {
            case "0":
                holder.tab_statu2.setText("未上传");
                holder.tab_statu2.setBackgroundResource(R.drawable.tab_item_gray);
                break;
            case "1":
                holder.tab_statu2.setText("通过");
                holder.tab_statu2.setBackgroundResource(R.drawable.tab_item_green);
                break;
            case "2":
                holder.tab_statu2.setText("通过");
                holder.tab_statu2.setBackgroundResource(R.drawable.tab_item_green);
                break;
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tab_str;
        TextView tab_content;
        TextView tab_user;
        TextView tab_time;
        TextView tab_fixed_tiem;
        TextView tab_view;
        TextView tab_statu2;
    }

    public void getDate(ArrayList<Tab_fragment_item> mDate) {
        this.mData = mDate;
        notifyDataSetChanged();
    }

}
