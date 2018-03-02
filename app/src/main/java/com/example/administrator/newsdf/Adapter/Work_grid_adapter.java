package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.View.PieChartBeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on
 */
/**
 * description:饼状图下发的分类适配器
 * @author: lx
 * date:2018/1/24 0024.
 * update: 2018/2/6 0006
 * version:
*/

public class Work_grid_adapter extends BaseAdapter {
    private Context mContext;
    private List<PieChartBeans> list;

    public Work_grid_adapter(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fr_work_gr_item, parent, false);
            holder = new ViewHolder();
            holder.home_item_img = (TextView) convertView.findViewById(R.id.fr_worl_gr_text);
            holder.fr_worl_gr_img = convertView.findViewById(R.id.fr_worl_gr_img);
            //将Holder存储到convertView中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.home_item_img.setText(list.get(position).getValuer());
        holder.fr_worl_gr_img.setBackgroundColor(Color.parseColor(list.get(position).getColor()));
        return convertView;
    }

    public void getData(List<PieChartBeans> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView home_item_img;
        ImageView fr_worl_gr_img;

    }
}
