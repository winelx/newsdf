package com.example.administrator.newsdf.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;


import java.util.ArrayList;


/**
 * 作者：winelx
 * 时间：2017/12/1 0001:上午 9:50
 * 说明：主界面recycr适配器
 */
public class Handoveradapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private ArrayList<String> mDatas = new ArrayList<>();

    private View mHeaderView;

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(ArrayList<String> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinglun, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
        final String data = mDatas.get(pos);
        if (viewHolder instanceof Holder) { //这是头部
            ((Holder) viewHolder).Title.setText("ss");
            ((Holder) viewHolder).details_data.setText("ss");
            ((Holder) viewHolder).details_content.setText("ss");
            ((Holder) viewHolder).details_in_user.setText("ss");
            ((Holder) viewHolder).details_user.setText("ss");
            ((Holder) viewHolder).details_end_data.setText("ss");
            ((Holder) viewHolder).details_boolean.setText("ss");
            ((Holder) viewHolder).handover_status_status.setText("ss");
            ((Holder) viewHolder).handover_status_content.setText("ss");
            ((Holder) viewHolder).handover_status_description.setText("ss");

            if (mListener == null) return;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos, data);
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView Title;
        private TextView details_data,//创建时间
                details_content,//内容
                details_in_user,//被转交人
                details_user,//转交人
                details_end_data,//最后时间
                details_boolean,//状态
                handover_status_status,//转交的状态，是否同意
                handover_status_content,//回复转交的说明
                handover_status_description; //转交说明
        private LinearLayout handover_description,//转交说明父布局
                details_in,//被转交人父布局
                handover_status;//是否转交父布局
        RecyclerView mRecycler;

        public Holder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            mRecycler = (RecyclerView) itemView.findViewById(R.id.handover_part_item);
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position, String data);
    }
}