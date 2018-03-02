package com.example.administrator.newsdf.activity.work;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.ZoomImageView;

/**
 * Created by Administrator on 2017/12/29 0029.
 */

public class ImageActivity extends AppCompatActivity {
    private ZoomImageView zooImageView;
    private Dates dates;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_image);
        dates = new Dates();
        zooImageView = (ZoomImageView) findViewById(R.id.imgage);
        Intent intent = getIntent();
        String path = intent.getExtras().getString("img");
        dates.getImg(this, path, zooImageView);
    }
}
