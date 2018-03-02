package com.example.administrator.newsdf.activity.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;

public class PchooseActivity extends AppCompatActivity {
    private Context mContext;
    private TextView com_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pchoose);
        mContext = PchooseActivity.this;
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("图纸查看");

        findViewById(R.id.pchoose_atlas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, PhotoListActivity.class));
            }
        });
        findViewById(R.id.pchoose_wbs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MmissPushActivity.class);
                intent.putExtra("data", "Photo");
                intent.putExtra("title", "图纸查看");
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
