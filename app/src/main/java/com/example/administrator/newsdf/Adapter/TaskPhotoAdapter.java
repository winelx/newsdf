package com.example.administrator.newsdf.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.administrator.newsdf.Bean.PhotoBean;
import com.example.administrator.newsdf.R;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPreview;

/**
 * Created by Administrator on 2018/2/7 0007.
 */

public class TaskPhotoAdapter extends BaseAdapter implements ListAdapter {
    //图册
    private ArrayList<PhotoBean> imagePaths;
    private LayoutInflater inflater;
    private ArrayList<String> mData;
    private ArrayList<String> title;
    private Context mContext;

    public TaskPhotoAdapter(ArrayList<PhotoBean> imagePaths, Context context) {
        super();
        this.imagePaths = imagePaths;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return imagePaths.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.taskphoto_item, null);
            holder = new ViewHolder();
            holder.pop_tast_item = convertView.findViewById(R.id.pop_task_item);
            //将Holder存储到convertView中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.pop_tast_item.setText(imagePaths.get(position).getDrawingName());
        holder.pop_tast_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData = new ArrayList<>();
                title = new ArrayList<String>();
                for (int i = 0; i < imagePaths.size(); i++) {
                    mData.add(imagePaths.get(i).getFilePath());
                    title.add(imagePaths.get(i).getDrawingName());
                }
                PhotoPreview.builder().setPhotos(mData).setCurrentItem(position).setShowDeleteButton(false).setTitle(title)
                        .start((Activity) mContext);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView pop_tast_item;
    }

    public void getData(ArrayList<PhotoBean> imagePaths) {
        this.imagePaths = imagePaths;
        notifyDataSetChanged();
    }
}
