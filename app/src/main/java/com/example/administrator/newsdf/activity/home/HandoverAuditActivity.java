package com.example.administrator.newsdf.activity.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.R;

import static com.example.administrator.newsdf.R.id.handover_false;

/**
 * @author lx
 * 转交审核 完成
 */
public class HandoverAuditActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView combutton, detailsdata, detailscontent, detailstitle, details_in_user,
            detailsuser, detailsenddata, detailsboolean, handoverstatuscontent, handoverstatusstatus, handoverstatusdescription;
    private LinearLayout handovertrue, handoverfalse;
    private ImageView handovertrueimg, handoverfalseimg;
    private EditText handovereditext;
    private boolean status;
    private LinearLayout handover_status, handover_lean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handover_audit);
        findViewById(R.id.com_back).setOnClickListener(this);
        handovereditext = (EditText) findViewById(R.id.handover_editext);//输入内容
        handoverfalse = (LinearLayout) findViewById(handover_false);//不同意
        handovertrue = (LinearLayout) findViewById(R.id.handover_true);//同意
        handovertrueimg = (ImageView) findViewById(R.id.handover_false_img);
        handoverfalseimg = (ImageView) findViewById(R.id.handover_true_img);
        detailsboolean = (TextView) findViewById(R.id.details_boolean);//状态
        detailsenddata = (TextView) findViewById(R.id.details_end_data);//最后时间
        detailsuser = (TextView) findViewById(R.id.details_user);//负责人
        detailscontent = (TextView) findViewById(R.id.details_content);//内容
        detailsdata = (TextView) findViewById(R.id.details_data);//时间
        detailstitle = (TextView) findViewById(R.id.details_title);//标题
        combutton = (TextView) findViewById(R.id.com_button);//提交
        handover_lean = (LinearLayout) findViewById(R.id.handover_lean);
        details_in_user = (TextView) findViewById(R.id.details_in_user);//被转交人
        handoverstatuscontent = (TextView) findViewById(R.id.handover_status_content);//转交说明
        handoverstatusstatus = (TextView) findViewById(R.id.handover_status_status);//转交是否同意
        handoverstatusdescription = (TextView) findViewById(R.id.handover_status_description);//转交审批意见
        handover_status = (LinearLayout) findViewById(R.id.handover_status);
        handover_status.setVisibility(View.GONE);
        combutton.setText("回复");
        handovertrueimg.setImageResource(R.mipmap.agree);
        handoverfalseimg.setImageResource(R.mipmap.disagree);
        combutton.setOnClickListener(this);
        handoverfalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HandoverAuditActivity.this, "通过", Toast.LENGTH_SHORT).show();
                handovertrueimg.setImageResource(R.mipmap.agree);
                handoverfalseimg.setImageResource(R.mipmap.disagree);
                status = true;
            }
        });
        handovertrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HandoverAuditActivity.this, "不通过", Toast.LENGTH_SHORT).show();
                handoverfalseimg.setImageResource(R.mipmap.agree);
                handovertrueimg.setImageResource(R.mipmap.disagree);
                status = false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_button://提交
                Toast.makeText(this, "提交", Toast.LENGTH_SHORT).show();
                handover_lean.setVisibility(View.GONE);
                handover_status.setVisibility(View.VISIBLE);
                String str = handovereditext.getText().toString();
                handoverstatuscontent.setText(str);
                if (status != false) {
                    handoverstatusstatus.setText("同意");
                } else {
                    handoverstatusstatus.setText("不同意");
                }
                break;
            case R.id.com_back://返回
                finish();

        }
    }
}
