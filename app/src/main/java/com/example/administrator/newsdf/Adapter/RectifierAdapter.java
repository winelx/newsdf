package com.example.administrator.newsdf.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Dates;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPreview;


/**
 * description:
 * @author winelx
 * date:2017/11/30 0030:下午 14:46
 * update: 2018/3/1 0001
 * version:
*/

public class RectifierAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mData;
    private ArrayList<String> Title;
    private Dates dates = new Dates();

    public RectifierAdapter(Context mContext, ArrayList<String> listA) {
        this.mContext = mContext;
        this.mData = listA;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rectifier_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeHolder) {
            bindView((TypeHolder) holder, position);
        }
    }

    private void bindView(final TypeHolder holder, final int position) {

        Glide.with(mContext).load(mData.get(position)).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPreview.builder().setPhotos(mData).setCurrentItem(position).setShowDeleteButton(false)
                        .start((Activity) mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TypeHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public TypeHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgage);
        }
    }

    public void getData(ArrayList<String> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

}
