package com.example.administrator.newsdf.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsdf.R;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class IndexFrament extends Fragment implements View.OnClickListener {
    private View rootView;
    private HomeFragment home;
    private AllMessageFragment message;
    private TextView mMessage, aMessage;
    private String status;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//避免重复绘制界面
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_index, null);
            mMessage = rootView.findViewById(R.id.fr_index_mm);
            aMessage = rootView.findViewById(R.id.fr_index_am);
            mContext = getActivity();
            mMessage.setOnClickListener(this);
            aMessage.setOnClickListener(this);

        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        //第一次初始化首页默认显示第一个fragment
        initFragment2();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    //显示第一个fragment
    private void initFragment1() {
        mMessage.setTextColor(Color.parseColor("#5096F8"));
        aMessage.setTextColor(Color.parseColor("#f5f4f4"));
        aMessage.setBackgroundResource(R.drawable.fr_home_mm_f);
        mMessage.setBackgroundResource(R.drawable.fr_home_am);
        status = "false";
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //第二种方式(replace)，初始化fragment
        if (home == null) {
            home = new HomeFragment();
        }
        transaction.replace(R.id.main_frame_layout, home);
        //提交事务
        transaction.commit();
    }

    //显示第一个fragment
    private void initFragment2() {
        aMessage.setTextColor(Color.parseColor("#5096F8"));
        mMessage.setTextColor(Color.parseColor("#f5f4f4"));
        aMessage.setBackgroundResource(R.drawable.fr_home_mm);
        mMessage.setBackgroundResource(R.drawable.fr_home_am_f);
        status = "true";
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //第二种方式(replace)，初始化fragment
        if (message == null) {
            message = new AllMessageFragment();
        }
        transaction.replace(R.id.main_frame_layout, message);
        //提交事务
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v == mMessage) {
            initFragment1();
        } else if (v == aMessage) {
            initFragment2();
        }
    }
}
