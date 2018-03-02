package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.newsdf.Bean.List_interface;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.SlantedTextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/26 0026.
 */
/**
 * description: 我的任务列表适配器
 * @author: lx
 * date: 2018/2/6 0006 上午 9:39
 * update: 2018/2/6 0006
 * version:
*/
public class Listinter_Adfapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<List_interface> mData;


    public Listinter_Adfapter(Context mContext, ArrayList<List_interface> mData) {
        this.mContext = mContext;
        this.mData = mData;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.interface_mine_item, parent, false);
            holder = new ViewHolder();
            //标题
            holder.inter_title = convertView.findViewById(R.id.inter_title);
            //创建时间
            holder.inter_data = convertView.findViewById(R.id.inter_data);
            //回复内容
            holder.inter_content = convertView.findViewById(R.id.inter_content);
            //状态
            holder.inter_text = convertView.findViewById(R.id.inter_text);
            //时间
            holder.item_inface_date = convertView.findViewById(R.id.item_inface_date);
            //状态三角
            holder.home_item_message = convertView.findViewById(R.id.home_item_message);
            //将Holder存储到convertView中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.inter_title.setText(mData.get(position).getPointName());
        //创建时间
        holder.inter_data.setText(mData.get(position).getWbsPath());
        //更新时间
        holder.item_inface_date.setText(mData.get(position).getCreateTime());
        //内容
        holder.inter_content.setText(mData.get(position).getContent());
        switch (mData.get(position).getIsFinish() + "") {
            //未完成
            case "0":
                holder.inter_text.setText("点击上传");
                holder.home_item_message.setTextString("未完成");
                holder.home_item_message.setSlantedBackgroundColor(R.color.Orange);
                break;
            //已完成
            case "1":
                holder.inter_text.setText("点击查看");
                holder.home_item_message.setTextString("已完成");
                holder.home_item_message.setSlantedBackgroundColor(R.color.finish_green);
            default:
                break;
        }

        return convertView;
    }

    static class ViewHolder {
        TextView inter_title, inter_data, inter_content, inter_text;
        AppCompatTextView item_inface_date;
        SlantedTextView home_item_message;
    }

    public void getDate(ArrayList<List_interface> mDate) {
        this.mData = mDate;
        notifyDataSetChanged();
    }

}