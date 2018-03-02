package com.example.administrator.newsdf.Adapter;

/**
 * Created by Administrator on 2017/12/27 0027.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.work.NotuploadActivity;
import com.example.administrator.newsdf.utils.LeftSlideView;
import com.example.administrator.newsdf.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 这里未上传资料的recycler的适配器
 */
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements LeftSlideView.IonSlidingButtonListener {

    private Context mContext;

    private List<Shop> mDatas = new ArrayList<Shop>();

    private IonSlidingViewClickListener mIDeleteBtnClickListener;

    private IonSlidingViewClickListener mISetBtnClickListener;

    private LeftSlideView mMenu = null;

    public Adapter(Context context) {
        mContext = context;
        mIDeleteBtnClickListener = (IonSlidingViewClickListener) context;
        mISetBtnClickListener = (IonSlidingViewClickListener) context;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mContext);
        //时间
        holder.time.setText(mDatas.get(position).getTimme());
        //输入内容
        if (mDatas.get(position).getContent().length() != 0) {
            holder.content.setText(mDatas.get(position).getContent());
        } else {
            holder.content.setText("暂未输入");
        }
        if (mDatas.get(position).getName().length() != 0) {
            holder.wbsnam.setText(mDatas.get(position).getName());
        } else {
            holder.wbsnam.setText("暂未选择");
        }

        holder.title.setText(mDatas.get(position).getCheckname());

        //item正文点击事件
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    NotuploadActivity activity = (NotuploadActivity) mContext;
                    activity.getInt(position);
                }
            }
        });
        //左滑设置点击事件
        holder.btn_Set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = holder.getLayoutPosition();
                mISetBtnClickListener.onSetBtnCilck(view, n);
            }
        });

        //左滑删除点击事件
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotuploadActivity activity = (NotuploadActivity) mContext;
                activity.deleteDate(position);
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {

        //获取自定义View的布局（加载item布局）
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item, arg0, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView btn_Set, title;
        public TextView btn_Delete;
        public TextView content, time, wbsnam;
        public ViewGroup layout_content;
        public RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            btn_Set = (TextView) itemView.findViewById(R.id.tv_set);
            btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
            layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            wbsnam = itemView.findViewById(R.id.wbsname);
            ((LeftSlideView) itemView).setSlidingButtonListener(Adapter.this);
        }
    }

    /**
     * 删除item
     *
     * @param position
     */
    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (LeftSlideView) view;
    }

    /**
     * 滑动或者点击了Item监听
     *
     * @param leftSlideView
     */
    @Override
    public void onDownOrMove(LeftSlideView leftSlideView) {
        if (menuIsOpen()) {
            if (mMenu != leftSlideView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断菜单是否打开
     *
     * @return
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }

    public void getData(List<Shop> shops) {
        mDatas = shops;
        notifyDataSetChanged();
    }

    /**
     * 注册接口的方法：点击事件。在Mactivity.java实现这些方法。
     */
    public interface IonSlidingViewClickListener {

        void onDeleteBtnCilck(View view, int position);//点击“删除”

        void onSetBtnCilck(View view, int position);//点击“设置”
    }
}