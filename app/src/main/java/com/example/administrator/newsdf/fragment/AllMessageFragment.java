package com.example.administrator.newsdf.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.AllMessageAdapter;
import com.example.administrator.newsdf.Bean.Home_item;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.ListreadActivity;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class AllMessageFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View rootView;
    private ListView listView;
    private AllMessageAdapter mAdapter = null;
    private ArrayList<Home_item> mData;
    private Context mContext;
    private SmartRefreshLayout refreshLayout;
    private RelativeLayout home_frag_img;
    private TextView home_img_text;
    private ImageView home_img_nonews;

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            mContext = getActivity();
            listView = rootView.findViewById(R.id.home_list);
            home_img_nonews = rootView.findViewById(R.id.home_img_nonews);
            home_frag_img = rootView.findViewById(R.id.home_frag_img);
            home_img_text = rootView.findViewById(R.id.home_img_text);
            refreshLayout = rootView.findViewById(R.id.SmartRefreshLayout);
            //设置在listview上下拉刷新的监听
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        init();
        return rootView;
    }


    private void init() {
        mData = new ArrayList<>();
        mAdapter = new AllMessageAdapter(mContext);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        home_frag_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dates.getDialog(getActivity(), "请求数据中");
                Okgo();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Okgo();
                //传入false表示刷新失败
                refreshlayout.finishRefresh(2000);
            }
        });
    }

    /**
     *  网络请求
     */
    private void Okgo() {
        Dates.getDialog(getActivity(), "请求数据中...");
        OkGo.post(Request.TaskMain)
                .params("isAll", "true")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.indexOf("data") != -1) {
                            Dates.disDialog();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                int code = jsonObject.getInt("ret");
                                if (code == 0) {
                                    listView.setVisibility(View.VISIBLE);
                                    mData.clear();
                                }
                                String msg = jsonObject.getString("msg");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String content = json.getString("content");
                                    String createTime = json.getString("createTime");
                                    if (createTime != null && !"".equals(createTime)) {
                                        createTime = createTime.substring(0, 10);
                                    } else {
                                        createTime = "";
                                    }
                                    String id = json.getString("id");
                                    String orgId = json.getString("orgId");
                                    String orgName = json.getString("orgName");
                                    String unfinish = json.getString("unfinish");
                                    mData.add(new Home_item(content, createTime, id, orgId, orgName, unfinish));
                                }
                                if (mData.size() != 0) {
                                    mAdapter.getDate(mData);
                                    home_frag_img.setVisibility(View.GONE);
                                    Dates.disDialog();
                                } else {
                                    home_frag_img.setVisibility(View.VISIBLE);
                                    home_img_text.setText("数据为空，点击刷新");
                                    Dates.disDialog();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            home_frag_img.setVisibility(View.VISIBLE);
                            home_img_text.setText("数据为空，点击刷新");
                            Dates.disDialog();
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast("网络连接失败");
                        home_frag_img.setVisibility(View.VISIBLE);
                        home_img_nonews.setBackgroundResource(R.mipmap.nonetwork);
                        home_img_text.setText("请求确认网络是否正常，点击再次请求");
                        Dates.disDialog();
                    }
                });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转列表界面
        Intent intent = new Intent(mContext, ListreadActivity.class);
        intent.putExtra("name", mData.get(position).getOrgname());
        intent.putExtra("orgId", mData.get(position).getOrgid());
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        Okgo();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        refreshLayout.finishRefresh(false);
    }
}