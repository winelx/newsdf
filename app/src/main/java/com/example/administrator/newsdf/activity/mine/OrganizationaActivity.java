package com.example.administrator.newsdf.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.Adapter.SettingAdapter;
import com.example.administrator.newsdf.Bean.Makeup;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.SPUtils;
import com.example.administrator.newsdf.utils.list.XListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.R.id.member;

/**
 * 切换组织界面
 */
public class OrganizationaActivity extends AppCompatActivity implements XListView.IXListViewListener {
    private XListView mTree;
    private SettingAdapter mAdapter = null;
    private ArrayList<Makeup> mData = new ArrayList<>();
    private Context mContext;
    private SPUtils mSpUtils;
    private LinearLayout home_backgroud;
    private TextView home_backgroud_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizationa);
        Dates.getDialog(OrganizationaActivity.this, "请求数据中...");
        mContext = OrganizationaActivity.this;
        mSpUtils = new SPUtils();
        okgo();
        mData = new ArrayList<>();
        mTree = (XListView) findViewById(R.id.organ_list_item);
        mTree.setPullRefreshEnable(true);
        mTree.setPullLoadEnable(false);
        mTree.setAutoLoadEnable(false);
        mTree.setXListViewListener(this);
        mTree.setRefreshTime(getTime());
        home_backgroud_text = (TextView) findViewById(R.id.home_backgroud_text);
        home_backgroud = (LinearLayout) findViewById(R.id.home_backgroud);
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new SettingAdapter<Makeup>(mData, R.layout.member_item) {
            @Override
            public void bindView(ViewHolder holder, final Makeup obj) {
                //名字
                holder.setText(R.id.member_name, obj.getName());
                holder.setOnClickListener(member, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        member(obj.getId(), obj.getName());
//
                    }
                });
            }

        };
        mTree.setAdapter(mAdapter);
    }

    //请求列表哦
    void okgo() {
        OkGo.<String>post(Request.Swatchmakeup)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.indexOf("data") != -1) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                    String orgID = jsonObject1.getString("organizationId");
                                    JSONObject JSONObject11 = jsonObject1.getJSONObject("organization");
                                    String name = JSONObject11.getString("name");
                                    mData.add(new Makeup(name, orgID));
                                }
                                if (mData.size() != 0) {
                                    mAdapter.getData(mData);
                                    home_backgroud.setVisibility(View.GONE);
                                } else {
                                    home_backgroud_text.setText("数据加载失败，试试下拉刷新");
                                    home_backgroud.setVisibility(View.VISIBLE);
                                }
                                Dates.disDialog();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Dates.disDialog();
                            home_backgroud_text.setText(R.string.text_nupoint);
                            home_backgroud.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        home_backgroud_text.setText("网络连接失败");
                        home_backgroud.setVisibility(View.VISIBLE);
                    }
                });
    }

    //切换组织
    void member(final String orgid, final String name) {
        OkGo.post(Request.Swatch)
                .params("orgId", orgid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String msg = jsonObject.getString("msg");
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                SPUtils.deleShare(mContext, "orgName");
                                SPUtils.deleShare(mContext, "orgId");
                                //所在组织ID
                                SPUtils.putString(mContext, "orgId", orgid);
                                //所在组织名称
                                SPUtils.putString(mContext, "username", name);
                                finish(); //此方法后才能返回主Activity
                            } else {
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRefresh() {
        mData.clear();
        okgo();
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                onLoad();
                return false;
                //表示延迟3秒发送任务
            }
        }).sendEmptyMessageDelayed(0, 1000);

    }

    @Override
    public void onLoadMore() {

    }

    private void onLoad() {
        mTree.stopRefresh();
        mTree.stopLoadMore();
        mTree.setRefreshTime(getTime());
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
}
