package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Bean.Inface_all_item;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.SlantedTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class ListreadAdapter extends BaseAdapter {
    private Context mContext;
    private List<Inface_all_item> mData;
    private Dates dates = new Dates();

    public ListreadAdapter(Context mContext) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.interface_item, parent, false);
            holder = new ViewHolder();
            holder.inter_title = (TextView) convertView.findViewById(R.id.home_item_img);
            holder.inter_time = (TextView) convertView.findViewById(R.id.home_item_img);
            holder.inface_wbs_path = (TextView) convertView.findViewById(R.id.home_item_img);
            holder.inter_content = (TextView) convertView.findViewById(R.id.home_item_img);
            holder.inface_username = (TextView) convertView.findViewById(R.id.home_item_img);
            holder.inface_uptime = (TextView) convertView.findViewById(R.id.home_item_img);
            holder.inface_pcontent = (TextView) convertView.findViewById(R.id.home_item_img);
            holder.inface_loation = (TextView) convertView.findViewById(R.id.home_item_img);
            holder.inface_status_true = convertView.findViewById(R.id.inface_status_true);
            convertView.setTag(holder);   //将Holder存储到convertView中
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //标题
        holder.inter_title.setText(mData.get(position).getGroupName());
        //创建时间
        holder.inter_time.setText(mData.get(position).getUpdateDate());
        //wbs路径
        holder.inface_wbs_path.setText(mData.get(position).getWbsPath());
        //内容
        holder.inter_content.setText(mData.get(position).getContent());
        //用户名
        holder.inface_username.setText(mData.get(position).getUploador());
        //回复时间
        holder.inface_uptime.setText(mData.get(position).getUpload_time());
        //定位
        holder.inface_loation.setText(mData.get(position).getUpload_addr());
        //回复内容
        holder.inface_pcontent.setText(mData.get(position).getUpload_content());
        Dates.getImg(mContext, mData.get(position).getPortrait(), holder.inface_avatar);
        switch (mData.get(position).getIsFinish() + "") {
            //未完成
            case "0":
                holder.inface_status_true.setVisibility(View.GONE);
                holder.inface_item_message.setTextString("未完成");
                holder.inface_item_message.setSlantedBackgroundColor(R.color.red);
                break;
            //已完成
            case "1":
                holder.inface_item_message.setTextString("已完成");
                holder.inface_item_message.setSlantedBackgroundColor(R.color.finish_green);
            default:
                break;
        }
        return convertView;
    }


    static class ViewHolder {
        TextView inter_title, inter_time, inface_wbs_path, inter_content, inface_username, inface_uptime,
                inface_pcontent, inface_loation;
        ImageView inface_avatar;
        SlantedTextView inface_item_message;
        GridView inface_grid;
        RelativeLayout inface_status_true;
    }

    public void getDate(List<Inface_all_item> mDate) {
        this.mData = mDate;
        notifyDataSetChanged();
    }

}