package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Dates;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public class AudioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_XBANNR = 0xff01;
    public static final int TYPE_GRID = 0xff02;
    public static final int TYPE_LIST = 0xff03;

    private ArrayList<Aduio_content> content;
    private ArrayList<Aduio_data> datas;
    private ArrayList<Aduio_comm> comms;

    private RecycleAtataAdapterType dataTypeAdapter;
    private RecycleCommAdapterType commlistBTypeAdapter;
    private Context mContext;

    //构造
    public AudioAdapter(Context mContext) {
        this.mContext = mContext;
        content = new ArrayList<>();
        datas = new ArrayList<>();
        comms = new ArrayList<>();
    }

    //type的类型分类
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_XBANNR:
                return new TypeBannerHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.handover_content, parent, false));
            case TYPE_GRID:
                return new TypeGridHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.handover_part_item, parent, false));
            case TYPE_LIST:
                return new TypeListHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.handover_list_item, parent, false));
            default:
                return null;
        }
    }

    //绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeBannerHolder && content.size() != 0) {
            bindBAnner((TypeBannerHolder) holder, position);
        } else if (holder instanceof TypeGridHolder && datas.size() != 0) {
            bindGrid((TypeGridHolder) holder, position);
        } else if (holder instanceof TypeListHolder && comms.size() != 0) {
            bindList((TypeListHolder) holder, position);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    //获取itemtype类型
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_XBANNR;
        } else if (position == 1) {
            return TYPE_GRID;
        } else if (position == 2) {
            return TYPE_LIST;
        } else {
            return TYPE_LIST;
        }
    }

    //控件数据处理
    private void bindBAnner(TypeBannerHolder holder, final int posotion) {
        if (content.size() != 0) {
            holder.linearLayout.setVisibility(View.VISIBLE);
            //标题
            holder.details_title.setText(content.get(posotion).getName());
            //创建时间
            String data = null;
            try {
                data = Dates.datato(content.get(posotion).getCreateDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.details_data.setText(content.get(posotion).getCreateDate() + "  " + data);
            holder.details_content.setText(content.get(posotion).getContent());
            holder.details_fixed_data.setText(content.get(posotion).getBackdata());
            //转交人
            holder.details_user.setText(content.get(posotion).getLeaderName());
            Log.d("bindBAnner", content.get(posotion).getStatus());
            if (content.get(posotion).getStatus().equals("0")) {
                //状态
                holder.details_boolean.setText("未完成");
                //状态
                holder.details_boolean.setTextColor(mContext.getResources().getColor(R.color.Orange));
            } else {
                //状态
                holder.details_boolean.setText("已完成");
                //状态
                holder.details_boolean.setTextColor(Color.GREEN);
            }
// 转交说明
            holder.handover_status_description.setText(content.get(posotion).getCreateDate());
        }
    }

    /**
     * 提交数据
     *
     * @param holder
     * @param posotion
     */

    private void bindGrid(TypeGridHolder holder, final int posotion) {
        if (datas.size() != 0) {
            holder.setpin.setVisibility(View.VISIBLE);
            holder.dataRec.setLayoutManager(new LinearLayoutManager(holder.dataRec.getContext(), LinearLayoutManager.VERTICAL, false));
            dataTypeAdapter = new RecycleAtataAdapterType(mContext);
            holder.dataRec.setAdapter(dataTypeAdapter);
            dataTypeAdapter.getdata(datas);
        }


    }

    /**
     * 评论
     *
     * @param holder
     * @param posotion
     */
    private void bindList(TypeListHolder holder, final int posotion) {
        if (comms.size() != 0) {
            holder.textView.setVisibility(View.VISIBLE);
            holder.ListRec.setLayoutManager(new GridLayoutManager(holder.
                    ListRec.getContext(), 1, GridLayoutManager.VERTICAL, false));
            commlistBTypeAdapter = new RecycleCommAdapterType(mContext);
            holder.ListRec.setAdapter(commlistBTypeAdapter);
            holder.ListRec.setNestedScrollingEnabled(false);
            commlistBTypeAdapter.getComm(comms);
        }

    }

    //ViewHolder初始化控件
    public class TypeBannerHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView details_title, details_data,
                details_user, details_boolean,
                handover_status_description, handover_huifu,
                details_content, details_fixed_data;

        public TypeBannerHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            details_title = itemView.findViewById(R.id.details_title);
            details_data = itemView.findViewById(R.id.details_data);
            details_fixed_data = itemView.findViewById(R.id.details_end_data);
            details_user = itemView.findViewById(R.id.details_user);
            details_boolean = itemView.findViewById(R.id.details_boolean);
            handover_status_description = itemView.findViewById(R.id.handover_status_description);
            details_content = itemView.findViewById(R.id.details_content);
            handover_huifu = itemView.findViewById(R.id.handover_fhui);
        }
    }

    public class TypeGridHolder extends RecyclerView.ViewHolder {
        RecyclerView dataRec;
        TextView setpin;

        public TypeGridHolder(View itemView) {
            super(itemView);
            dataRec = (RecyclerView) itemView.findViewById(R.id.handover_part_item);
            setpin = itemView.findViewById(R.id.handovers_text);
        }
    }

    public class TypeListHolder extends RecyclerView.ViewHolder {
        RecyclerView ListRec;
        TextView textView;

        public TypeListHolder(View itemView) {
            super(itemView);
            ListRec = (RecyclerView) itemView.findViewById(R.id.handover_part_item);
            textView = itemView.findViewById(R.id.handover_text);
        }
    }

    //数据源
    public void setmBanner(ArrayList<Aduio_content> content) {
        this.content = content;
        notifyDataSetChanged();
    }

    public void getmListA(ArrayList<Aduio_data> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void getmListB(ArrayList<Aduio_comm> comms) {
        this.comms = comms;
        notifyDataSetChanged();
    }


}