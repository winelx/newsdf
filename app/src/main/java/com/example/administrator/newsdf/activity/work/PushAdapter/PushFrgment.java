package com.example.administrator.newsdf.activity.work.PushAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.Adapter.PushfragmentAdapter;
import com.example.administrator.newsdf.Bean.Push_item;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.LazyFragment;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.WbsDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * 作者：winelx
 * 时间：2017/12/2 0002:下午 16:49
 * 说明：
 */
@SuppressLint("ValidFragment")
public class PushFrgment extends LazyFragment {
    private int pos = 0;
    private Context mContext;
    private ArrayList<Push_item> data = null;
    View view;
    private ListView mContentRlv;
    private RelativeLayout push_img;
    //定义控件
    private Button btn_delete;
    private CheckBox che_all;
    private TextView push_img_text;
    boolean status = false;
    private ImageView push_img_nonew;
    //定义自定义适配器
    private PushfragmentAdapter myAdapter;
    private WbsDialog selfDialog;
    List<String> deleSelect;//存放选中项的集合
    String content,//内容
            id,//ID
            label,//标签
            leaderName,//推送人
            sendTime,//推送时间
            sendTimes,//推送次数
            preconditionsName;//前置项
    private View mEmptyView;
    private TextView push_jing;

    //获取当前是第几个界面
    @SuppressLint("ValidFragment")
    public PushFrgment(int pos) {
        this.pos = pos;
    }

    private int mPage = 1;
    private int mIndex = 1;
    private SmartRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.push, container, false);
        data = new ArrayList<>();
        mContext = getActivity();
        push_img_nonew = view.findViewById(R.id.push_img_nonew);
        push_img = view.findViewById(R.id.push_img);
        push_img_text = view.findViewById(R.id.push_img_text);
        mContentRlv = (ListView) view.findViewById(R.id.lv_data);
        myAdapter = new PushfragmentAdapter(mContext);
        mContentRlv.setAdapter(myAdapter);
        refreshLayout = view.findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                okgo();
                che_all.setChecked(false);
                //传入false表示刷新失败
                refreshlayout.finishRefresh(1500/*,false*/);
            }
        });

        //获取控件
        btn_delete = (Button) view.findViewById(R.id.btn_delete);
        che_all = (CheckBox) view.findViewById(R.id.che_all);
        push_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dates.getDialog(getActivity(), "请求数据中");
                okgo();
                che_all.setChecked(false);

            }
        });
        initlistener();
        okgo();
        return view;
    }

    public void checkbox() {
        che_all.setChecked(false);
    }

    private void initlistener() {
        /**
         * 全选复选框设置事件监听
         */
        che_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (data.size() != 0) {//判断列表中是否有数据
                    if (isChecked) {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setChecked(true);
                        }
                        //通知适配器更新UI
                        myAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setChecked(false);
                        }
                        //通知适配器更新UI
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        //推送按钮点击事件
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个要推送内容的集合，不能直接在数据源data集合中直接进行操作，否则会报异常
                deleSelect = new ArrayList<String>();
                //把选中的条目要推送的条目放在deleSelect这个集合中
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getChecked()) {
                        deleSelect.add(data.get(i).getId());
                    }
                }
                //判断用户是否选中要推送的数据及是否有数据
                if (deleSelect.size() != 0 && data.size() != 0) {
                    String strids = Dates.listToString(deleSelect);
                    pushOkgo(strids);
                } else if (data.size() == 0) {
                    Toast.makeText(getActivity(), "没有要推送的数据", Toast.LENGTH_SHORT).show();
                } else if (deleSelect.size() == 0) {
                    Toast.makeText(getContext(), "请选中要推送的数据", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Dates.disDialog();
        che_all.setChecked(false);
    }

    //请求界面
    void okgo() {
        OkGo.post(Request.PUSHList)
                .params("wbsId", PushAdapter.Content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.indexOf("data") != -1) {
                            data.clear();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < 1; i++) {
                                    JSONObject json = jsonArray.getJSONObject(pos);
                                    JSONArray jsonArr = json.getJSONArray("casePointsList");
                                    for (int j = 0; j < jsonArr.length(); j++) {
                                        JSONObject josn1 = jsonArr.getJSONObject(j);
                                        try {
                                            content = josn1.getString("content");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            content = "";
                                        }
                                        try {
                                            id = josn1.getString("id");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            id = "";
                                        }
                                        try {
                                            label = josn1.getString("label");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            label = "";
                                        }
                                        try {
                                            leaderName = josn1.getString("leaderName");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            leaderName = "";
                                        }
                                        try {
                                            sendTime = josn1.getString("sendTime");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            sendTime = "";
                                        }
                                        try {
                                            sendTimes = josn1.getString("sendTimes");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            sendTimes = "";
                                        }
                                        try {
                                            preconditionsName = josn1.getString("preconditionsName");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            preconditionsName = "";
                                        }
                                        data.add(new Push_item(content, id, label, preconditionsName, leaderName, sendTime, sendTimes, false));
                                    }
                                    if (data.size() != 0) {
                                        myAdapter.getData(data);
                                        push_img.setVisibility(View.GONE);
                                        Dates.disDialog();
                                    } else {
                                        push_img.setVisibility(View.VISIBLE);
                                        push_img_text.setText("数据为空，点击刷新");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        push_img.setVisibility(View.VISIBLE);
                        push_img_nonew.setBackgroundResource(R.mipmap.nonetwork);
                        push_img_text.setText("网络请求失败，点击刷新");
                    }
                });
    }

    //推送请求
    void pushOkgo(String str) {
        OkGo.post(Request.pushOKgo)
                .params("ids", str)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String msg = jsonObject.getString("msg");
                            int ret = jsonObject.getInt("ret");
                            ToastUtils.showShortToast(msg);
                            if (ret == 0) {
                                //把deleSelect集合中的数据清空
                                deleSelect.clear();
                                //把全选复选框设置为false
                                che_all.setChecked(false);
                                //通知适配器更新UI
                                okgo();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        //把全选复选框设置为false
        che_all.setChecked(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        che_all.setChecked(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        che_all.setChecked(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        //把全选复选框设置为false
        che_all.setChecked(false);
    }

    @Override
    public void LazyLoad() {

    }


}