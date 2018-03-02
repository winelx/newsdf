package com.example.administrator.newsdf.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Bean.Push_item;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.work.PushdialogActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class PushfragmentAdapter extends BaseAdapter {
    private Dialog mCameraDialog;

    //定义一个数据源的引用
    private ArrayList<Push_item> data;
    private Context context;
    private PopupWindow mPopWindow;

    public PushfragmentAdapter(Context context) {
        this.context = context;

        data = new ArrayList<>();
    }


    /**
     * 获取当前子view的id（就是listview中的每一个条目的位置）
     *
     * @param position
     * @return 返回当前id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取当前子view对应的值
     *
     * @param position 当前子view（条目）的id（位置）
     * @return 返回当前对应的值 该值为object类型
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }


    /**
     * 核心代码
     *
     * @param position    当前子view的id
     * @param convertView 缓存布局（该view与子view保持一致）
     * @param parent      父容器（即当前listview）
     * @return 返回当前子view（包含布局及具体的数据）
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //布局生成器(抽象类)
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        //声明缓存
        ViewHodler viewHodler = null;
        //重新创建布局及缓存
        if (convertView == null) {
            //创建缓存布局
            convertView = layoutInflater.inflate(R.layout.push_fragment_item, parent, false);
            //产生缓存
            viewHodler = new ViewHodler();
            viewHodler.tvTitle = (TextView) convertView.findViewById(R.id.tab_str);
            viewHodler.tvContent = (TextView) convertView.findViewById(R.id.tab_content);
            viewHodler.tvUser = (TextView) convertView.findViewById(R.id.tab_user);
            viewHodler.tab_fixed_number = (TextView) convertView.findViewById(R.id.tab_fixed_number);
            viewHodler.ch_delete = (CheckBox) convertView.findViewById(R.id.tab_checkBox);
            viewHodler.recycler_view = convertView.findViewById(R.id.recycler_view);
            //把缓存的布局放在converview中，避免重复获取布局，提升效率
            convertView.setTag(viewHodler);
        } else {
            //使用缓存的中的布局
            viewHodler = (ViewHodler) convertView.getTag();
        }
        //为缓存的布局ViewHodler控件设置新的数据
        String content;//所属名称
        String id;//ID
        String label;//内容
        String preconditionsCurid;//先决条件
        String leaderName;//负责人
        String sendTime;//发送时间
        String sendTimes;//发送次数

        Push_item currItem = data.get(position);
        viewHodler.tvTitle.setText(currItem.getLabel());
        viewHodler.tvContent.setText(currItem.getContent());
        viewHodler.tvUser.setText(currItem.getLeaderName());
        viewHodler.ch_delete.setChecked(currItem.getChecked());
        viewHodler.tab_fixed_number.setText(currItem.getSendTimes());
        //listView单个条目事件监听
        viewHodler.ch_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getChecked()) {
                    data.get(position).setChecked(false);
                    notifyDataSetChanged();
                } else {
                    data.get(position).setChecked(true);
                    notifyDataSetChanged();
                }
            }
        });

        viewHodler.recycler_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PushdialogActivity.class);
                intent.putExtra("content", data.get(position).getLabel());//内容
                intent.putExtra("requirements", data.get(position).getContent());//要求
                intent.putExtra("title", data.get(position).getPreconditionsCurid());
                intent.putExtra("user", data.get(position).getLeaderName());
                intent.putExtra("number", data.get(position).getSendTimes());//推送次数
                context.startActivity(intent);

            }
        });
        return convertView;
    }

    /**
     * 获取数据中要在listview中显示的条目
     *
     * @return 返回数据的条目
     */
    @Override
    public int getCount() {
        return this.data != null ? this.data.size() : 0;
    }

    public void getData(ArrayList<Push_item> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    /**
     * 定义coverView的Recyler(缓存)，该类名自定义的
     */
    class ViewHodler {
        TextView tvTitle, tvContent, tvUser, tab_fixed_number;
        CheckBox ch_delete;
        RelativeLayout recycler_view;
    }

}
