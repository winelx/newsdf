package com.example.administrator.newsdf.activity.work.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.TabAdapters;
import com.example.administrator.newsdf.Bean.Tab_fragment_item;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.AuditparticularsActivity;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.LazyFragment;
import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.activity.work.Adapter.TabAdapter.wbeID;


@SuppressLint("ValidFragment")
public class TabFragment extends LazyFragment {
    private int pos = 0;

    private TabAdapter TabAdapter = null;
    private TabAdapters mAdapters;
    private ArrayList<Tab_fragment_item> mData = null;
    private Context mContext;
    private ListView listVIew;
    private View view;
    private int i = 1;
    boolean status = false;
    private SmartRefreshLayout refreshLayout;
    private RelativeLayout tab_frag_img;
    private TextView tab_img_text;

    @SuppressLint("ValidFragment")
    public TabFragment(int pos) {
        this.pos = pos;

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag, container, false);
        mContext = getActivity();
        mData = new ArrayList<>();
        refreshLayout = view.findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                i = 1;
                status = true;
                okgo(TabAdapter.ids.get(pos), 1);
                refreshlayout.finishRefresh(1500/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                i = i + 1;
                status = false;
                Log.i("tabloading", i + "");
                okgo(TabAdapter.ids.get(pos), i);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });

        tab_frag_img = view.findViewById(R.id.tab_frag_img);
        tab_img_text = view.findViewById(R.id.tab_img_text);
        listVIew = view.findViewById(R.id.shLv);
        mAdapters = new TabAdapters(mContext);
        listVIew.setAdapter(mAdapters);
        listVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = mData.get(position).getStatus1();

                switch (str) {
                    case "0":
                        Intent intent = new Intent(mContext, AuditparticularsActivity.class);
                        intent.putExtra("id", mData.get(position).getId());
                        intent.putExtra("name", mData.get(position).getUser());
                        intent.putExtra("frag_id", mData.get(position).getId());
                        intent.putExtra("status", "one");
                        intent.putExtra("wbsid", wbeID);
                        startActivity(intent);
                        break;
                    case "1":
                        Intent intent1 = new Intent(mContext, AuditparticularsActivity.class);
                        intent1.putExtra("frag_id", mData.get(position).getId());
                        intent1.putExtra("wbsid", wbeID);
                        startActivity(intent1);
                        break;
                    case "2":
                        Intent intent2 = new Intent(mContext, AuditparticularsActivity.class);
                        intent2.putExtra("frag_id", mData.get(position).getId());
                        intent2.putExtra("status", "two");
                        intent2.putExtra("wbsid", wbeID);
                        startActivity(intent2);
                        break;
                    default:
                        break;
                }
            }
        });

        tab_frag_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dates.getDialog(getActivity(), "请求数据中");
                mData.clear();
                okgo(TabAdapter.ids.get(pos), 1);
            }
        });
        return view;
    }

    void okgo(final String str, final int e) {
        OkGo.post(Request.WbsTaskMain)
                .params("qaGroupId", str)
                .params("page", e)
                .params("rows", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("ss", s);
                        if (s.indexOf("data") != -1) {
                            try {
                                if (status == true) {
                                    mData.clear();
                                }
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String name;
                                    try {
                                        name = json.getString("name");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        name = "";
                                    }
                                    String id;
                                    try {
                                        id = json.getString("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        id = "";
                                    }
                                    String statu;
                                    try {
                                        statu = json.getString("status");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        statu = "";
                                    }
                                    String content;
                                    try {
                                        content = json.getString("content");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        content = "";
                                    }
                                    String leaderName;
                                    try {
                                        leaderName = json.getString("leaderName");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        leaderName = "";
                                    }
                                    String createDate;
                                    try {
                                        createDate = json.getString("createDate");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        createDate = "";
                                    }
                                    mData.add(new Tab_fragment_item(id, name, content, leaderName, statu, createDate));
                                }
                                if (mData.size() != 0) {
                                    mAdapters.getDate(mData);
                                    tab_frag_img.setVisibility(View.GONE);
                                    Dates.disDialog();
                                } else {
                                    Dates.disDialog();
                                    ToastUtils.showShortToast("没有更多数据了！");
                                    tab_frag_img.setVisibility(View.VISIBLE);
                                    tab_img_text.setText("没有数据");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //是上拉加载，那么就不能显示
                            if (mData.size() != 0) {
                                ToastUtils.showShortToast("没有更多数据了！");
                            } else {
                                tab_frag_img.setVisibility(View.VISIBLE);
                                tab_img_text.setText("没有数据");
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        tab_frag_img.setVisibility(View.VISIBLE);
                        tab_img_text.setText("请求确定网络是否");
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        //重新加载数据，
        status = true;
        okgo(TabAdapter.ids.get(pos) + "", 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Dates.disDialog();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void LazyLoad() {
    }

}