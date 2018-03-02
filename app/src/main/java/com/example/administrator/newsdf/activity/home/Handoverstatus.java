package com.example.administrator.newsdf.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;

/**
 * 作者：winelx
 * 时间：2017/11/30 0030:上午 11:12
 * 说明：转交任务状态，是否同意  完成
 */
public class Handoverstatus extends AppCompatActivity implements View.OnClickListener {
    private TextView combutton, detailsdata, detailscontent, detailstitle, details_in_user,
            detailsuser, detailsenddata, detailsboolean, handoverstatuscontent, handoverstatusstatus, handoverstatusdescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handover_status);
        findViewById(R.id.com_back).setOnClickListener(this);
        detailsboolean = (TextView) findViewById(R.id.details_boolean);//状态
        detailsenddata = (TextView) findViewById(R.id.details_end_data);//最后时间
        detailsuser = (TextView) findViewById(R.id.details_user);//负责人
        detailscontent = (TextView) findViewById(R.id.details_content);//内容
        detailsdata = (TextView) findViewById(R.id.details_data);//时间
        detailstitle = (TextView) findViewById(R.id.details_title);//标题
        combutton = (TextView) findViewById(R.id.com_button);//提交
        details_in_user = (TextView) findViewById(R.id.details_in_user);//被转交人
        handoverstatuscontent = (TextView) findViewById(R.id.handover_status_content);//转交说明
        handoverstatusstatus = (TextView) findViewById(R.id.handover_status_status);//转交是否同意
        handoverstatusdescription = (TextView) findViewById(R.id.handover_status_description);//转交审批意见
        combutton.setText("回复");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
        }
    }
}
