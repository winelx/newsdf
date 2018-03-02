package com.example.administrator.newsdf.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 *         启动页
 */
public class BootupActivity extends AppCompatActivity {

    private Context mContext;

    private final int SDK_PERMISSION_REQUEST = 127;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bootup);
        mContext = BootupActivity.this;
        Dates.getSHA1(getApplicationContext());
        getPersimmions();
        final String user = SPUtils.getString(BootupActivity.this, "user", "");
//        final String password = SPUtils.getString(BootupActivity.this, "password", "");
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //进行是否登录判断
                if (TextUtils.isEmpty(user)) {
                    //实现页面跳转
                    startActivity(new Intent(BootupActivity.this, LoginActivity.class));
                    finish();
                } else {
                    //实现页面跳转
                    startActivity(new Intent(BootupActivity.this, MainActivity.class));
                    finish();
                    // okgo(user, password);
                }
                return false;
            }
            //表示延迟3秒发送任务
        }).sendEmptyMessageDelayed(0, 2500);
    }

    private void okgo(final String user, final String passowd) {
        OkGo.post(Request.networks)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        login(user, passowd);
                    }

                    //这个错误是网络级错误，不是请求失败的错误
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }

    private void login(final String user, final String password) {
        OkGo.post(Request.Login)
                .params("username", user)
                .params("password", password)
                .params("mobileLogin", true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String msg, Call call, Response response) {
                        Log.i("msg", msg);
                        try {
                            JSONObject jsonObject = new JSONObject(msg);
                            int str = jsonObject.getInt("ret");
                            if (str == 0) {
                                JSONObject jsom = jsonObject.getJSONObject("data");
                                String id = jsom.getString("id");
                                //头像 需要拼接公共头
                                String portrait = jsom.getString("portrait");
                                //职员ID
                                String staffId = jsom.getString("staffId");
                                ;//所在组织名称
                                String orgName = jsom.getString("orgName");
                                //真实姓名
                                String staffName = jsom.getString("staffName");
                                //所在组织id
                                String orgId = jsom.getString("orgId");
                                //所在组织id
                                String phone = jsom.getString("phone");
                                SPUtils.deleAll(mContext);
                                //职员ID
                                SPUtils.putString(mContext, "staffId", staffId);
                                //所在组织名称
                                SPUtils.putString(mContext, "username", orgName);
                                //真实姓名
                                SPUtils.putString(mContext, "staffName", staffName);
                                ; //id
                                SPUtils.putString(mContext, "id", id);
                                //头像
                                SPUtils.putString(mContext, "portrait", portrait);
                                //头像
                                SPUtils.putString(mContext, "orgId", orgId);
                                //头像
                                SPUtils.putString(mContext, "phone", phone);
                                //是否保存数据
                                SPUtils.putString(mContext, "user", user);
                                SPUtils.putString(mContext, "password", password);
                                startActivity(new Intent(BootupActivity.this, MainActivity.class));
                                finish();
                            } else if (str == 1) {
                                startActivity(new Intent(BootupActivity.this, LoginActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }
}
