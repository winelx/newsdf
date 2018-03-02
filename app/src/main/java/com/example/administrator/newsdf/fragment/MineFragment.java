package com.example.administrator.newsdf.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.LoginActivity;
import com.example.administrator.newsdf.activity.mine.AboutmeActivity;
import com.example.administrator.newsdf.activity.mine.OrganizationaActivity;
import com.example.administrator.newsdf.activity.mine.PasswordActvity;
import com.example.administrator.newsdf.activity.mine.PersonalActivity;
import com.example.administrator.newsdf.activity.mine.ProjectmemberActivity;
import com.example.administrator.newsdf.activity.mine.SettingActivity;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.AppUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.SPUtils;
import com.example.administrator.newsdf.utils.UpdateService;
import com.example.administrator.newsdf.utils.WbsDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class MineFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private CircleImageView mine_avatar;
    private Context mContext;
    private SPUtils spUtils;
    private Dates dates;
    private TextView mine_organization, staffName;
    private List<Shop> list;
    private Intent intent;
    String version;
    private TextView mine_uploading;
    private WbsDialog selfDialog;
    String sd = "1.2";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//避免重复绘制界面
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_mine, null);
            rootView.findViewById(R.id.organizationa).setOnClickListener(this);//我的组织
            rootView.findViewById(R.id.projectmember).setOnClickListener(this);//项目成员
            rootView.findViewById(R.id.changepassword).setOnClickListener(this);//修改密码
            rootView.findViewById(R.id.mine_setting).setOnClickListener(this);//系统设置
            rootView.findViewById(R.id.about_us).setOnClickListener(this);//关于我们
            rootView.findViewById(R.id.newversion).setOnClickListener(this);//检查新版本
            rootView.findViewById(R.id.mine_Thecache).setOnClickListener(this);//清除缓存
            rootView.findViewById(R.id.mine_avatar).setOnClickListener(this);//头像
            rootView.findViewById(R.id.mine_organization).setOnClickListener(this);//所在组织
            rootView.findViewById(R.id.staffName).setOnClickListener(this);//名字
            rootView.findViewById(R.id.BackTo).setOnClickListener(this);//退出
            mine_uploading = rootView.findViewById(R.id.mine_uploadings);
            mine_avatar = rootView.findViewById(R.id.mine_avatar);
            mine_organization = rootView.findViewById(R.id.mine_organization);
            staffName = rootView.findViewById(R.id.staffName);
        }
        mContext = getActivity();
        spUtils = new SPUtils();
        dates = new Dates();
        version = AppUtils.getVersionName(getActivity());
        ititData();
        //  upload();
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void ititData() {
        String url = Request.networks + SPUtils.getString(mContext, "portrait", null);
        Glide.with(this)
                .load(url)
                .thumbnail(Glide.with(this)
                        .load(R.mipmap.mine_avatar))
                .into(mine_avatar);
        mine_organization.setText(SPUtils.getString(mContext, "username", ""));
        staffName.setText(SPUtils.getString(mContext, "staffName", ""));
        list = new ArrayList<>();
        list = LoveDao.queryLove();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //我的组织
            case R.id.organizationa:
                startActivity(new Intent(getActivity(), OrganizationaActivity.class));
                break;
            //项目成员
            case R.id.projectmember:
                startActivity(new Intent(getActivity(), ProjectmemberActivity.class));
                break;
            //修改密码
            case R.id.changepassword:
                startActivity(new Intent(getActivity(), PasswordActvity.class));
                break;
            //系统设置
            case R.id.mine_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            //关于我们
            case R.id.about_us:
                startActivity(new Intent(getActivity(), AboutmeActivity.class));
                break;
            //个人信息
            case R.id.mine_avatar:
                startActivity(new Intent(getActivity(), PersonalActivity.class));
                break;
            case R.id.BackTo:
                Okgo();
                startActivity(new Intent(mContext, LoginActivity.class));

                getActivity().finish();
                break;
            case R.id.mine_Thecache:
                Dates.getDialog(getActivity(), "清理缓存...");
//                Dates.deleteAllFiles(new File("/storage/emulated/0/pictures"));
//                Dates.deleteAllFiles(new File("/storage/emulated/0/Pictures/cropUtils"));
                if (list.size() == 0) {
                    //如果资料上传的还有记录，那就不能删除本地文件，
                    Dates.deleteAllFiles(new File(Environment.getExternalStorageDirectory() + "/Boohee/"));
                }
                new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        Dates.disDialog();
                        Toast.makeText(mContext, "缓存清除成功", Toast.LENGTH_SHORT).show();
                        return false;
                        //表示延迟3秒发送任务
                    }
                }).sendEmptyMessageDelayed(0, 1000);
                break;
            //检查新版本
            case R.id.newversion:
//                OkGo.<String>post(Request.UpLoading)
//                        .params("type", 1)
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(String s, Call call, Response response) {
//                                if (s.indexOf("data") != -1) {
//                                    try {
//                                        JSONObject jsonObject = new JSONObject(s);
//                                        int ret = jsonObject.getInt("ret");
//                                       if (ret == 0) {
//                                            JSONObject json = jsonObject.getJSONObject("data");
//                                            String versions = json.getString("version");//版本号
//                                            String filePath = json.getString("filePath");//更新地址
//                                            int lenght = version.compareTo(versions);
//                                          if (lenght < 0) {
//                                         ToastUtils.showShortToast("有新版本需要更新");
//                                                intent = new Intent(getActivity(), UpdateService.class);
//                                                intent.putExtra("data", filePath);
//                                                mContext.startService(intent);
//                                          } else {
//                                               ToastUtils.showShortToast("已经是最新本版本");
//                                          }
//                                       }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                } else {
//
//                                }
//                            }
//                        });
                break;
            default:
                break;


        }
    }

    /**
     * 退出登录
     */

    private void Okgo() {
        OkGo.post(Request.BackTo)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("logup", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int code = jsonObject.getInt("ret");
                            if (code != 1) {
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
        //可能会切换组织，所以每次走start时重新请求数据
        mine_organization.setText(SPUtils.getString(mContext, "username", ""));
        staffName.setText(SPUtils.getString(mContext, "staffName", ""));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 自动检测升级
     */
    void upload() {
        OkGo.<String>post(Request.UpLoading)
                .params("type", 1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.indexOf("data") != -1) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                int ret = jsonObject.getInt("ret");
                                if (ret == 0) {
                                    JSONObject json = jsonObject.getJSONObject("data");
                                    //版本号
                                    String versions = json.getString("version");
                                    //更新地址
                                    String filePath = json.getString("filePath");
                                    int lenght = version.compareTo(versions);
                                    if (lenght < 0) {
                                        ToastUtils.showShortToast("有新版本需要更新");
                                        mine_uploading.setVisibility(View.VISIBLE);
                                        show(filePath);
                                    } else {

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }
                });
    }

    void show(final String path) {
        selfDialog = new WbsDialog(mContext);
        selfDialog.setTitle("更新提示");
        selfDialog.setMessage("检测到有新版本，是否更新");
        selfDialog.setYesOnclickListener("确定", new WbsDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                intent = new Intent(getActivity(), UpdateService.class);
                intent.putExtra("data", path);
                mContext.startService(intent);
                selfDialog.dismiss();
            }
        });
        selfDialog.setNoOnclickListener("取消", new WbsDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        selfDialog.show();
    }
}

