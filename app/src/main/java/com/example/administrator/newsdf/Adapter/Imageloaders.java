package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.administrator.newsdf.Bean.Inface_all_item;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.SlantedTextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.administrator.newsdf.R.id.inface_item_message;
import static com.example.administrator.newsdf.R.id.inface_status_true;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class Imageloaders extends BaseAdapter {
    private Context context;
    private List<Inface_all_item> list;
    private ListView listview;
    private LruCache<String, BitmapDrawable> mImageCache;
    private ImageLoader imageLoader;
    private RequestQueue queue;

    public Imageloaders(Context context, List<Inface_all_item> list) {
        super();
        this.context = context;
        this.list = list;
        queue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(queue, new BitmapCache());
        int maxCache = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxCache / 8;
        mImageCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return value.getBitmap().getByteCount();
            }
        };

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (listview == null) {
            listview = (ListView) parent;
        }
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.interface_item, null);
            holder = new ViewHolder();
            holder.inface_image = convertView.findViewById(R.id.inface_image);
            holder.inface_image3 = convertView.findViewById(R.id.inface_image3);
            holder.inface_image2 = convertView.findViewById(R.id.inface_image2);
            holder.inface_imag1 = convertView.findViewById(R.id.inface_image1);
            holder.user_auathor = convertView.findViewById(R.id.inface_avatar);
            holder.inter_title = convertView.findViewById(R.id.inter_title);
            holder.inter_time = convertView.findViewById(R.id.inter_time);
            holder.inface_wbs_path = convertView.findViewById(R.id.inface_wbs_path);
            holder.inter_content = convertView.findViewById(R.id.inter_content);
            holder.inface_username = convertView.findViewById(R.id.inface_username);
            holder.inface_uptime = convertView.findViewById(R.id.inface_uptime);
            holder.inface_pcontent = convertView.findViewById(R.id.inface_pcontent);
            holder.inface_loation = convertView.findViewById(R.id.inface_loation);
            holder.inface_imgae_text = convertView.findViewById(R.id.inface_imgae_text);
            holder.inface_status_true = convertView.findViewById(inface_status_true);
            holder.inface_item_message = convertView.findViewById(inface_item_message);
            holder.textView4 = convertView.findViewById(R.id.textView4);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.inter_title.setText(list.get(position).getGroupName());
        String str = null;
        try {
            str = Dates.datato(list.get(position).getCreateTime());
            holder.inter_time.setText(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.inface_wbs_path.setText(list.get(position).getWbsPath());
        holder.inter_content.setText(list.get(position).getContent());
        holder.inface_username.setText(list.get(position).getUploador());
        holder.inface_uptime.setText(list.get(position).getUpload_time());
        holder.inface_loation.setText(list.get(position).getUpload_addr());
        holder.inface_pcontent.setText(list.get(position).getUpload_content());
        ArrayList<String> path = new ArrayList<>();
        path.addAll(list.get(position).getUpload());
        Dates.setback(context, list.get(position).getPortrait(), holder.user_auathor);

        switch (list.get(position).getIsFinish() + "") {
            //未完成
            case "0":
                holder.inface_item_message.setTextString("未完成");
                holder.inface_item_message.setSlantedBackgroundColor(R.color.Orange);
                holder.inface_status_true.setVisibility(View.GONE);
                break;
            case "1":
                holder.inface_status_true.setVisibility(View.VISIBLE);
                holder.inface_item_message.setTextString("已完成");
                holder.textView4.setText("(" + list.get(position).getComments() + ")");
                holder.inface_item_message.setSlantedBackgroundColor(R.color.finish_green);
                // 预设一个图片
                if (path.size() >= 1) {
                    holder.inface_image.setVisibility(View.VISIBLE);
                    String imgUrl = path.get(0);
                    if (imgUrl != null && !imgUrl.equals("")) {
                        holder.inface_imgae_text.setVisibility(View.GONE);
                        holder.inface_image2.setVisibility(View.INVISIBLE);
                        holder.inface_image3.setVisibility(View.INVISIBLE);
                        holder.inface_imag1.setDefaultImageResId(R.mipmap.image_loading);
                        holder.inface_imag1.setErrorImageResId(R.mipmap.image_error);
                        holder.inface_imag1.setImageUrl(imgUrl, imageLoader);
                        if (path.size() >= 2) {
                            String imgUrl1 = path.get(1);
                            if (imgUrl1 != null && !imgUrl1.equals("")) {
                                holder.inface_image2.setVisibility(View.VISIBLE);
                                holder.inface_image3.setVisibility(View.INVISIBLE);
                                holder.inface_image2.setDefaultImageResId(R.mipmap.image_loading);
                                holder.inface_image2.setErrorImageResId(R.mipmap.image_error);
                                holder.inface_image2.setImageUrl(imgUrl1, imageLoader);
                                if (path.size() >= 3) {
                                    String imgUrl2 = path.get(2);
                                    if (imgUrl2 != null && !imgUrl2.equals("")) {
                                        if (path.size() > 3) {
                                            holder.inface_imgae_text.setVisibility(View.VISIBLE);
                                            int num = path.size() - 3;
                                            holder.inface_imgae_text.setText("+" + num);
                                        }
                                        holder.inface_image3.setVisibility(View.VISIBLE);
                                        holder.inface_image3.setDefaultImageResId(R.mipmap.image_loading);
                                        holder.inface_image3.setErrorImageResId(R.mipmap.image_error);
                                        holder.inface_image3.setImageUrl(imgUrl2, imageLoader);
                                    }
                                }

                            }
                        }
                    }
                } else {
                    holder.inface_image.setVisibility(View.GONE);
                }
            default:
                break;
        }
        return convertView;
    }

    class ViewHolder {
        NetworkImageView inface_image3, inface_image2, inface_imag1;
        CircleImageView user_auathor;
        RelativeLayout inface_status_true;
        TextView inter_title;
        TextView inter_time;
        TextView inface_wbs_path;
        TextView inter_content;
        TextView inface_username;
        TextView inface_uptime;
        TextView inface_pcontent;
        TextView inface_loation;
        TextView inface_imgae_text;
        TextView textView4;
        LinearLayout inface_image;
        private SlantedTextView inface_item_message;
    }

    public void getData(List<Inface_all_item> mdata) {
        this.list = mdata;
        notifyDataSetChanged();
    }

}
