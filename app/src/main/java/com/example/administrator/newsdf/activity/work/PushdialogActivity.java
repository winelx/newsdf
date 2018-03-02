package com.example.administrator.newsdf.activity.work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;

public class PushdialogActivity extends Activity {
    private String content, requirement, title, user, numner;
    private TextView poop_blen, pop_requirements, pop_conditions, pop_user, pop_pushnumber;
    private RelativeLayout lin_push_pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushdialog);
        poop_blen = findViewById(R.id.poop_blen);
        pop_requirements = findViewById(R.id.pop_requirements);
        pop_conditions = findViewById(R.id.pop_conditions);
        pop_user = findViewById(R.id.pop_user);
        pop_pushnumber = findViewById(R.id.pop_pushnumber);
        lin_push_pop = findViewById(R.id.lin_push_pop);
        Intent intent = getIntent();
        try {
            content = intent.getExtras().getString("content");//内容
            requirement = intent.getExtras().getString("requirements");//要求
            title = intent.getExtras().getString("title");//用户
            user = intent.getExtras().getString("user");////标题
            numner = intent.getExtras().getString("number");//推送次数
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        poop_blen.setText(content);//内容
        pop_requirements.setText(requirement);//要求
        pop_conditions.setText(title);//前置项
        pop_user.setText(user);//用户名
        pop_pushnumber.setText(numner);//推送次数

        lin_push_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
