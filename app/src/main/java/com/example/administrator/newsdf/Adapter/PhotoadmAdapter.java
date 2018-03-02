package com.example.administrator.newsdf.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Bean.PhotoBean;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Dates;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPreview;


/**
 * description: 图册查看适配器
 * @author: lx
 * date: 2018/2/6 0006 上午 9:40
 * update: 2018/2/6 0006
 * version:
*/
public class PhotoadmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<PhotoBean> mData;
    private Dates dates = new Dates();
    ArrayList<String> path;

    public PhotoadmAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photoadm_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeHolder) {
            bindView((TypeHolder) holder, position);
        }
    }

    private void bindView(final TypeHolder holder, final int position) {
        holder.photo_name.setText(mData.get(position).getDrawingGroupName());
        holder.photo_number.setText(mData.get(position).getDrawingNumber());
        holder.photo_names.setText(mData.get(position).getDrawingName());
        holder.lin_photo_adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = new ArrayList<>();
                for (int i = 0; i < mData.size(); i++) {
                    path.add(mData.get(i).getFilePath());
                }
                PhotoPreview.builder().setPhotos(path).setCurrentItem(position).setShowDeleteButton(false)
                        .start((Activity) mContext);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TypeHolder extends RecyclerView.ViewHolder {
        TextView photo_name, photo_number, photo_names;
        LinearLayout lin_photo_adm;

        public TypeHolder(View itemView) {
            super(itemView);
            photo_name = itemView.findViewById(R.id.photo_name);
            photo_number = itemView.findViewById(R.id.photo_number);
            photo_names = itemView.findViewById(R.id.photo_names);
            lin_photo_adm = itemView.findViewById(R.id.lin_photo_adm);
        }
    }

    public void getData(ArrayList<PhotoBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

}
