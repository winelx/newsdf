package com.example.administrator.newsdf.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Bean.DetailsBean;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.same.DirectlyreplyActivity;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * 转交或者回复 完成
 */
public class ListDetailsActivity extends AppCompatActivity {
    private TextView title;
    private TextView detailstitle;
    private TextView detailscontent;
    private TextView detailsuser, com_content;
    private TextView details_in_user;
    private TextView detailsboolean, handover_status_description;
    private RelativeLayout replyin;
    private TextView detailsdata;
    SPUtils spUtils;
    private Context mContext;
    private TextView com_button, wbspath;
    ArrayList<DetailsBean> detailsBeen;
    private LinearLayout handover_description, details_in;
    String id, checkid, usernma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdetails);
        spUtils = new SPUtils();
        mContext = ListDetailsActivity.this;
        //得到跳转到该Activity的Intent对象
        Intent intent = getIntent();
        id = intent.getExtras().getString("frag_id");
        detailsBeen = new ArrayList<>();
        usernma = SPUtils.getString(mContext, "staffName", null);
        findID();
        LinearLayout back = (LinearLayout) findViewById(R.id.com_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        com_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ListDetailsActivity.this, DirectlyreplyActivity.class);
                intent1.putExtra("id", id);
                startActivity(intent1);
            }
        });
        okgoone();
    }

    private void findID() {
        com_content = (TextView) findViewById(R.id.com_title);
        com_content.setText("任务详情");
        com_button = (TextView) findViewById(R.id.com_button);
        //负责人
        detailsuser = (TextView) findViewById(R.id.ListDetails_user);
        //内容
        detailscontent = (TextView) findViewById(R.id.ListDetails_content);
        //时间
        detailsdata = (TextView) findViewById(R.id.ListDetails_data);
        //标题
        detailstitle = (TextView) findViewById(R.id.ListDetails_title);
        //状态
        detailsboolean = (TextView) findViewById(R.id.ListDetails_boolean);
        //转交说明
        handover_status_description = (TextView) findViewById(R.id.ListDetails_status_description);
        //转交父级控件
        handover_description = (LinearLayout) findViewById(R.id.ListDetails_description);
        //被转交人
        details_in_user = (TextView) findViewById(R.id.ListDetails_in_user);
        //被转交人父级控件
        details_in = (LinearLayout) findViewById(R.id.ListDetails_in);
        wbspath = (TextView) findViewById(R.id.wbspath);

    }

    String changeReviewStatus;

    private void okgoone() {
        OkGo.post(Request.Detail)
                .params("wbsTaskId", id)
                .execute(new StringCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONObject json = jsonObject1.getJSONObject("wtMain");
                            JSONObject createBy = json.getJSONObject("createBy");
                            changeReviewStatus = json.getString("status");
                            //推送内容
                            detailscontent.setText(json.getString("content"));
                            //负责人
                            detailsuser.setText(json.getString("leaderName"));
                            //创建时间
                            detailsdata.setText(json.getString("createDate"));
                            wbspath.setText(json.getString("wbsName"));
                            //检查点
                            detailstitle.setText(json.getString("name"));
                            //唯一id
                            checkid = json.getString("id");
                            //负责人ID
                            String leaderId = json.getString("leaderId");
                            //是否已读
                            String isread = json.getString("isread");
                            //创建人ID
                            String createByUserID = createBy.getString("id");
                            //是否被打回过
                            String iscallback = json.getString("iscallback");
                            //wbsnama
                            String wbsName = json.getString("wbsName");
                            if (Objects.equals(changeReviewStatus, "2")) {
                                detailsboolean.setText("通过");
                            } else if (Objects.equals(changeReviewStatus, "3")) {
                                detailsboolean.setText("打回");
                            } else if (Objects.equals(changeReviewStatus, "4")) {
                                detailsboolean.setText("评论");
                            } else if (Objects.equals(changeReviewStatus, "0")) {
                                detailsboolean.setText("未上传");
                            }
                            if (usernma.equals(json.getString("leaderName"))) {
                                com_button.setText("回复");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                });
    }


}
