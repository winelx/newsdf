package com.example.administrator.newsdf.activity.work;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;

public class PopwindActivity extends Activity implements View.OnClickListener {

    private ImageView tree_error;
    private TextView tree_names, tree_ethnic, tree_conditions, tree_number;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tree_dialog);
        Intent intent = getIntent();
        phone = intent.getExtras().getString("moblie");
        findViewById(R.id.tree_lin_dialog).setOnClickListener(this);
        findViewById(R.id.tree_phone).setOnClickListener(this);
        findViewById(R.id.tree_error).setOnClickListener(this);
        tree_names = (TextView) findViewById(R.id.tree_names);
        tree_names.setText(intent.getExtras().getString("name"));
        tree_ethnic = (TextView) findViewById(R.id.tree_ethnic);
        tree_ethnic.setText(intent.getExtras().getString("ethnicities"));
        tree_conditions = (TextView) findViewById(R.id.tree_conditions);
        tree_conditions.setText(intent.getExtras().getString("orgName"));
        tree_number = (TextView) findViewById(R.id.tree_number);
        tree_number.setText(intent.getExtras().getString("moblie"));
        tree_number.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tree_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.tree_lin_dialog:
                this.finish();
                break;
            case R.id.tree_error:
                this.finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return true;
    }
}
