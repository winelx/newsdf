package com.example.administrator.newsdf.activity.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;

public class PushCheckActivity extends AppCompatActivity {
    private RelativeLayout pchoose_atlas, pchoose_wbs;
    private Context mContext;
    TextView com_titlle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_check);
        mContext = PushCheckActivity.this;
        com_titlle = (TextView) findViewById(R.id.com_title);
        com_titlle.setText("任务下发");
        findViewById(R.id.pchoose_atlas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MmissPushActivity.class);
                intent.putExtra("data", "details");
                intent.putExtra("title", "任务推送");
                startActivity(intent);
            }
        });
        findViewById(R.id.pchoose_wbs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MmissPushActivity.class);
                intent.putExtra("data", "push");
                intent.putExtra("title", "任务推送");
                startActivity(intent);
            }
        });
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
