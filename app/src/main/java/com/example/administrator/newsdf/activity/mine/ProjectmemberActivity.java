package com.example.administrator.newsdf.activity.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.SettingAdapter;
import com.example.administrator.newsdf.Bean.Icon;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.CheckPermission;
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
import java.util.List;
import java.util.Locale;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionItem;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 项目成员
 */
public class ProjectmemberActivity extends AppCompatActivity implements XListView.IXListViewListener {
    private XListView uslistView;
    private TextView textView, comtitle;
    private EditText useditext;
    private LinearLayout comback;
    private SettingAdapter mAdapter = null;
    private ArrayList<Icon> mData;
    private Context mContext;
    private CheckPermission checkPermission;
    private LinearLayout home_backgroud;
    private TextView home_backgroud_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectmember);
        Dates.getDialog(ProjectmemberActivity.this, "请求数据中...");
        mContext = ProjectmemberActivity.this;
        mData = new ArrayList<>();
        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.CALL_PHONE, getString(R.string.permission_cus_item_phone), R.drawable.permission_ic_phone));
        HiPermission.create(ProjectmemberActivity.this)
                .permissions(permissonItems)
                .checkMutiPermission(null);
        home_backgroud = (LinearLayout) findViewById(R.id.mine_backgroud);
        home_backgroud_text = (TextView) findViewById(R.id.mine_backgroud_text);
        uslistView = (XListView) findViewById(R.id.us_listView);
        uslistView.setPullRefreshEnable(true);
        uslistView.setPullLoadEnable(false);
        uslistView.setAutoLoadEnable(false);
        uslistView.setXListViewListener(this);
        uslistView.setRefreshTime(getTime());
        comtitle = (TextView) findViewById(R.id.com_title);
        comback = (LinearLayout) findViewById(R.id.com_back);
        okgo();
        mAdapter = new SettingAdapter<Icon>(mData, R.layout.setting_member_item) {
            @Override
            public void bindView(ViewHolder holder, final Icon obj) {
                //头像
                holder.setImages(R.id.circleImageView, obj.getImageUrl());
                //名字
                holder.setText(R.id.member_name, obj.getName());
                //手机号
                holder.setText(R.id.member_moblie, obj.getMoblie());
                holder.setOnClickListener(R.id.call_phone, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + obj.getMoblie()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        };
        comtitle.setText(SPUtils.getString(mContext, "username", ""));
        uslistView.setAdapter(mAdapter);
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //网络请求
    void okgo() {
        OkGo.post(Request.Members)
                .params("orgId", SPUtils.getString(mContext, "orgId", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.indexOf("data") != -1) {
                            mData.clear();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String str = jsonObject.getString("msg");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject content = jsonArray.getJSONObject(i);
                                    String id = content.getString("id");
                                    String userId = content.getString("userId");
                                    String name = content.getString("name");
                                    String moblie = content.getString("moblie");
                                    String imageUrl = content.getString("imageUrl");
                                    mData.add(new Icon(id, userId, name, moblie, imageUrl));
                                }
                                if (mData.size() != 0) {
                                    home_backgroud.setVisibility(View.GONE);
                                    mAdapter.getData(mData);
                                } else {
                                    home_backgroud.setVisibility(View.VISIBLE);
                                    home_backgroud_text.setText("数据加载失败，试试下拉刷新");
                                }
                                Dates.disDialog();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Dates.disDialog();
                            home_backgroud.setVisibility(View.VISIBLE);
                            home_backgroud_text.setText(R.string.text_nupoint);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                        home_backgroud_text.setText(R.string.text_okgo_error);
                        home_backgroud.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRefresh() {
        okgo();
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                onLoad();
                return false;
                //表示延迟3秒发送任务
            }
        }).sendEmptyMessageDelayed(0, 2000);

    }

    @Override
    public void onLoadMore() {

    }

    private void onLoad() {
        uslistView.stopRefresh();
        uslistView.stopLoadMore();
        uslistView.setRefreshTime(getTime());
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
}