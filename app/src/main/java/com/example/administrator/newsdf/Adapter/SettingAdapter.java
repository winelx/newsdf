package com.example.administrator.newsdf.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.SlantedTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 2015/9/22 0022.
 */
public abstract class SettingAdapter<T> extends BaseAdapter {

    private List<T> mData;
    private int mLayoutRes;           //布局id

    public SettingAdapter() {
    }

    public SettingAdapter(List<T> mData, int mLayoutRes) {
        this.mData = mData;
        this.mLayoutRes = mLayoutRes;
    }

    public void getData(List<T> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, mLayoutRes
                , position);
        bindView(holder, getItem(position));
        return holder.getItemView();
    }

    public abstract void bindView(ViewHolder holder, T obj);

    //添加一个元素
    public void add(T data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(data);
        notifyDataSetChanged();
    }

    //往特定位置，添加一个元素
    public void add(int position, T data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(T data) {
        if (mData != null) {
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (mData != null) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder {

        private SparseArray<View> mViews;   //存储ListView 的 item中的View
        private View item;                  //存放convertView
        private int position;               //游标
        private Context context;            //Context上下文

        //构造方法，完成相关初始化
        private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
            mViews = new SparseArray<>();
            this.context = context;
            View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
            convertView.setTag(this);
            item = convertView;
        }

        //绑定ViewHolder与item
        public static ViewHolder bind(Context context, View convertView, ViewGroup parent,
                                      int layoutRes, int position) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(context, parent, layoutRes);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.item = convertView;
            }
            holder.position = position;
            return holder;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int id) {
            T t = (T) mViews.get(id);
            if (t == null) {
                t = (T) item.findViewById(id);
                mViews.put(id, t);
            }
            return t;
        }


        /**
         * 获取当前条目
         */
        public View getItemView() {
            return item;
        }

        /**
         * 获取条目位置
         */
        public int getItemPosition() {
            return position;
        }

        /**
         * 设置文字
         */
        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        /**
         * 设置自定义控件文字和背景
         */
        public ViewHolder setSlantedText(int id, CharSequence text, int res) {
            View view = getView(id);
            if (view instanceof SlantedTextView) {
                ((SlantedTextView) view).setTextString(text.toString());
                ((SlantedTextView) view).setSlantedBackgroundColor(res);
            }
            return this;
        }


        /**
         * 设置图片
         */
        public ViewHolder setImageResource(int id, int drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }

        public ViewHolder setBackgroud(int id, String drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setBackgroundColor(Color.parseColor(drawableRes));
            }
            return this;
        }

        /**
         * 设置图片
         */
        public ViewHolder setbackGroude(int id, int drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((TextView) view).setBackgroundResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }

        /**
         * 设置网络图
         *
         * @param id
         * @param url
         * @return
         */
        public ViewHolder setImage(int id, String url) {
            View view = getView(id);
            if (view instanceof ImageView) {
                Glide.with(context)
                        .load(url)
                        .transition(new DrawableTransitionOptions().crossFade(2000))
                        .thumbnail(Glide.with(context)
                                .load(R.mipmap.image_loading))
                        .into((ImageView) view);
            }
            return this;
        }

        public ViewHolder setImages(int id, String url) {
            View view = getView(id);
            if (view instanceof ImageView) {
                Glide.with(context)
                        .load(url)
                        .transition(new DrawableTransitionOptions().crossFade(2000))
                        .thumbnail(Glide.with(context)
                                .load(R.mipmap.image_loading))
                        .into((ImageView) view);

            }
            return this;
        }

        public ViewHolder sethight(int id, int writh) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = writh;
                view.setLayoutParams(params);
            }
            return this;
        }

        public ViewHolder setavathorImage(int id, String url) {
            View view = getView(id);
            if (view instanceof ImageView) {
                Glide.with(context).load(url).into((ImageView) view);
            }
            return this;
        }

        /**
         * 设置点击监听
         */
        public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            getView(id).setOnClickListener(listener);
            return this;
        }

        /**
         * 设置可见
         */
        public ViewHolder setVisibility(int id, int visible) {
            getView(id).setVisibility(visible);
            return this;
        }

        /**
         * 设置标签
         */
        public ViewHolder setTag(int id, Object obj) {
            getView(id).setTag(obj);
            return this;
        }

        public ViewHolder setbackgroud(int id, int str) {
            getView(id).setBackgroundColor(str);
            return this;
        }

    }

}
