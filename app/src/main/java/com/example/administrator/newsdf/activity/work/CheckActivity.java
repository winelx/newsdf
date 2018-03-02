package com.example.administrator.newsdf.activity.work;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.example.administrator.newsdf.Adapter.PhotoAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.ListinterfaceActivity;
import com.example.administrator.newsdf.activity.home.same.ReplyActivity;
import com.example.administrator.newsdf.baseActivity;
import com.example.administrator.newsdf.camera.CheckPermission;
import com.example.administrator.newsdf.camera.CropImageUtils;
import com.example.administrator.newsdf.camera.ImageUtil;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.service.LocationService;
import com.example.administrator.newsdf.utils.Dates;

import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.SPUtils;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class CheckActivity extends AppCompatActivity implements View.OnClickListener {
    private PhotoAdapter photoAdapter;
    private ArrayList<String> imagePaths;
    private ArrayList<File> files;
    private RecyclerView photoadd;
    private String TAG = "ReplyActivity";
    private LocationService locationService;
    private TextView repley_address, wbs_text, com_button, title, tvNetSpeed;
    private ImageView address, baoxun, imgdsd;
    private String Latitude, Longitude;
    private EditText reply_text;
    private Context mContext;
    private ProgressBar mProgressBar;
    private LinearLayout Progessn;
    private SPUtils spUtils;
    private com.example.administrator.newsdf.utils.Dates Dates;
    LocationClientOption option;
    private CheckPermission checkPermission;
    //gaode
    private IconTextView re_back;
    String wbsID = null;
    LinearLayout lin_sdfg;
    String checkId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        setContentView(R.layout.activity_reply);
        mContext = CheckActivity.this;
        checkPermission = new CheckPermission(this) {
            @Override
            public void permissionSuccess() {
                CropImageUtils.getInstance().takePhoto(CheckActivity.this);
            }

            @Override
            public void negativeButton() {
                //如果不重写，默认是finishddsfaasf
                //super.negativeButton();
                ToastUtils.showLongToast("权限申请失败！");
            }
        };
        findID();   //发现ID
        title.setText("资料上传");
        imagePaths = new ArrayList<>();
        Intent intent = getIntent();
        checkId = intent.getExtras().getString("checkid");
        Dates = new Dates();//时间帮助类
        spUtils = new SPUtils();//sp帮助类
        location();
        initDate();//recycclerView
    }

    //定位
    private void location() {
        //定位初始化
        locationService = ((baseActivity) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
    }

    //发现ID
    private void findID() {
        lin_sdfg = (LinearLayout) findViewById(R.id.lin_sdfg);
        tvNetSpeed = (TextView) findViewById(R.id.tvNetSpeed);
        photoadd = (RecyclerView) findViewById(R.id.recycler_view); //图片
        repley_address = (TextView) findViewById(R.id.repley_address);
        reply_text = (EditText) findViewById(R.id.reply_text);
        address = (ImageView) findViewById(R.id.address);
        com_button = (TextView) findViewById(R.id.com_button);
        baoxun = (ImageView) findViewById(R.id.com_img);
        wbs_text = (TextView) findViewById(R.id.wbs_text);
        title = (TextView) findViewById(R.id.com_title);
        findViewById(R.id.reply_wbs).setOnClickListener(this);
        findViewById(R.id.reply_check).setOnClickListener(this);
        Progessn = (LinearLayout) findViewById(R.id.Progess);
        mProgressBar = (ProgressBar) findViewById(R.id.reply_bar);
        findViewById(R.id.com_back).setOnClickListener(this);
        lin_sdfg.setVisibility(View.GONE);
        com_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                files = new ArrayList<>();
                for (int i = 0; i < imagePaths.size(); i++) {
                    files.add(new File(imagePaths.get(i)));
                }
                getOkgo();
            }
        });
    }

    //调用相机、先进行全线判断
    public void Cream() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_CAMERA);
        } else {
            CropImageUtils.getInstance().takePhoto(this);
        }
    }

    //初始化recyclerview
    private void initDate() {
        photoAdapter = new PhotoAdapter(this, imagePaths);
        photoadd.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoadd.setAdapter(photoAdapter);
        com_button.setBackgroundResource(R.mipmap.reply_commit);
    }

    //网络请求
    void getOkgo() {
        //判断是否是消息进入的资料上传，是就用消息
        if (checkId.length() != 0) {
//            id 任务id
//            uploadContent 上传说明
//            latitude 纬度
//            longitude 经度
//            uploadAddr 上传地点（编译后的位置，如贵州省贵阳市……）
//            imagesList 附件列表

            OkGo.post(Request.Uploade)
                    .params("id", checkId)
                    .params("uploadContent", reply_text.getText().toString())
                    .params("latitude", Latitude)
                    .params("longitude", Longitude)
                    .params("uploadAddr", Bai_address)
                    .addFileParams("imagesList", files)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String msg = jsonObject.getString("msg");
                                int code = jsonObject.getInt("ret");
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                if (code == 0) {
                                    startActivity(new Intent(CheckActivity.this, ListinterfaceActivity.class));
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }


    //删除图片
    public void getremove(ArrayList<String> imgpath) {
        this.imagePaths = imgpath;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) { //判断是不是Activity的返回，不是就是相机的返回
            wbsID = data.getStringExtra("position");
            String titme = data.getStringExtra("title");
            wbs_text.setText(titme);
        } else {
            CropImageUtils.getInstance().onActivityResult(this, requestCode, resultCode, data, new CropImageUtils.OnResultListener() {
                @Override
                public void takePhotoFinish(String path) {
//                    ToastUtils.showLongToast("照片存放在：" + path);
                    //根据路径压缩图片并返回bitmap(2)
                    Bitmap bitmap = Dates.getBitmap(path);
                    //给压缩的图片添加时间水印(1)
                    String time = Dates.getDate();
                    Bitmap textBitmap = ImageUtil.drawTextToRightBottom(mContext,
                            bitmap, time + Bai_address, 15, Color.WHITE, 0, 0);
                    //保存添加水印的时间的图片
                    String str = Dates.saveImageToGallery(mContext, textBitmap);
                    //添加进集合
                    imagePaths.add(str);
                    //填入listview，刷新界面
                    photoAdapter.getData(imagePaths);
                    //删除原图
                    Dates.deleteFile(path);
                }
            });
        }
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private String Bai_address = null;
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                Latitude = location.getLatitude() + "";
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                Longitude = location.getLongitude() + "";
                sb.append("\naddr : ");// 地址信息
                Bai_address = location.getAddrStr();
                repley_address.setText(location.getAddrStr());
                locationService.stop();// 定位SDK
            }
        }
    };

}
