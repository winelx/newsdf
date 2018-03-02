package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Bean.Home_item;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.ListreadActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class AllMessageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Home_item> mData;

    public AllMessageAdapter(Context mContext) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_fragment_item, parent, false);
            holder = new ViewHolder();
            holder.home_item_img = (TextView) convertView.findViewById(R.id.home_item_img); //标段
            holder.home_item_name = (TextView) convertView.findViewById(R.id.home_item_name);
            holder.home_item_content = (TextView) convertView.findViewById(R.id.home_item_content);
            holder.home_item_time = (TextView) convertView.findViewById(R.id.home_item_time);
            holder.home_item_message = (TextView) convertView.findViewById(R.id.home_item_message);
            holder.lin = convertView.findViewById(R.id.lin);
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
        holder.home_item_message.setVisibility(View.GONE);
        String mess = mData.get(position).getUnfinish();
        if (mess.length() == 0) {
            holder.home_item_message.setVisibility(View.INVISIBLE);
            holder.home_item_message.setText(mess);

        }
        int Random = (int) (Math.random() * 4) + 1;
        if (Random == 1) {
            holder.home_item_img.setBackgroundResource(R.drawable.home_item_blue);
        } else if (Random == 2) {
            holder.home_item_img.setBackgroundResource(R.drawable.home_item_yello);
        } else if (Random == 3) {
            holder.home_item_img.setBackgroundResource(R.drawable.home_item_style);
        } else if (Random == 4) {
            holder.home_item_img.setBackgroundResource(R.drawable.homt_item_blue);
        }
        holder.home_item_img.setText(mData.get(position).getOrgname()); //前面圆圈
        holder.home_item_name.setText(mData.get(position).getOrgname()); //所属组织
        holder.home_item_content.setText(mData.get(position).getContent());//最后一条消息
        holder.home_item_time.setText(mData.get(position).getCreaeTime()); //最后一条消息时间
        holder.home_item_message.setText(mData.get(position).getUnfinish());
        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ListreadActivity.class);
                intent.putExtra("name", mData.get(position).getOrgname());
                intent.putExtra("back", mData.get(position).getOrgname());
                intent.putExtra("orgId", mData.get(position).getOrgid());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private void Visible(ViewHolder holder) {
        holder.home_item_message.setVisibility(View.VISIBLE);
        holder.home_item_boolean.setVisibility(View.GONE);
    }

    static class ViewHolder {
        TextView home_item_img;
        TextView home_item_name;
        TextView home_item_content;
        TextView home_item_time;
        TextView home_item_boolean;
        TextView home_item_message;
        RelativeLayout lin;
    }

    public void getDate(ArrayList<Home_item> mDate) {
        this.mData = mDate;
        notifyDataSetChanged();
    }
}
