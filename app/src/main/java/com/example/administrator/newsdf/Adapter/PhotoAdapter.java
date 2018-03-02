package com.example.administrator.newsdf.Adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.same.ReplyActivity;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPreview;


/**
 * Created by donglua on 15/5/31.
 * 添加图片
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private ArrayList<String> photoPaths;
    private LayoutInflater inflater;
    private Context mContext;

    public final static int TYPE_ADD = 1;
    final static int TYPE_PHOTO = 2;

    final static int MAX = 100;

    public PhotoAdapter(Context mContext, ArrayList<String> photoPaths) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TYPE_ADD:
                itemView = inflater.inflate(R.layout.item_add, parent, false);
                break;
            case TYPE_PHOTO:
                itemView = inflater.inflate(R.layout.picker_item_photo, parent, false);
                break;
            default:
                break;
        }
        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_PHOTO) {
            Glide.with(mContext)
                    .load(photoPaths.get(position))
                    .thumbnail(0.1f)
                    .into(holder.ivPhoto);
            holder.vSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photoPaths.remove(position);
                    notifyDataSetChanged();
                }
            });
            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> paths = new ArrayList<String>();
                    paths.addAll(photoPaths);
                    for (int i = 0; i < paths.size(); i++) {
                        Log.i("paths", paths.get(i));
                        Log.i("paths1", photoPaths.get(i));
                    }
                   PhotoPreview.builder().setPhotos(paths).setCurrentItem(position).setShowDeleteButton(false)
                           .start((Activity) mContext);
                }
            });
        } else {
            holder.img_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReplyActivity reply = (ReplyActivity) mContext;
                    reply.Cream();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int count = photoPaths.size() + 1;
        if (count > MAX) {
            count = MAX;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == photoPaths.size() && position != MAX) ? TYPE_ADD : TYPE_PHOTO;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private ImageView vSelected;
        private ImageView img_add;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            img_add = itemView.findViewById(R.id.bt_add);
        }
    }

    public void getData(ArrayList<String> photoPaths) {
        this.photoPaths = photoPaths;
        notifyDataSetChanged();
    }
}
