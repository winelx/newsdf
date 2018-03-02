package com.example.administrator.newsdf.activity.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;

/**
 * 详情阅读
 */
public class RequiredActivity extends AppCompatActivity {
    private TextView detailstitle, detailsdata, detailscontent, detailsinuser, detailsenddata, detailsboolean;
    private Button required;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_required);
        detailsboolean = (TextView) findViewById(R.id.details_boolean);//状态
        detailsenddata = (TextView) findViewById(R.id.details_end_data);//截止时间
        detailsinuser = (TextView) findViewById(R.id.details_in_user);//负责人
        detailscontent = (TextView) findViewById(R.id.details_content);//内容
        detailsdata = (TextView) findViewById(R.id.details_data);//时间
        detailstitle = (TextView) findViewById(R.id.details_title);//标题
        //已阅读
        findViewById(R.id.required).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //返回
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
