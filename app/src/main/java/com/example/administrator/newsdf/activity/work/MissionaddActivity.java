package com.example.administrator.newsdf.activity.work;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.PhotoAdapter;
import com.example.administrator.newsdf.R;

import java.util.ArrayList;


/**
 * 添加任务
 */
public class MissionaddActivity extends AppCompatActivity {
    private TextView com_title, com_button;
    private LinearLayout Lin_check, Lin_WBS;
    private RecyclerView mission_rec;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> imagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionadd);
        com_title = (TextView) findViewById(R.id.com_title);//toolbar标题
        com_button = (TextView) findViewById(R.id.com_button);//提交
        Lin_check = (LinearLayout) findViewById(R.id.Lin_check);//选择wes结构
        Lin_WBS = (LinearLayout) findViewById(R.id.Lin_WBS);//定位检查点
        mission_rec = (RecyclerView) findViewById(R.id.mission_rec);
        imagePaths = new ArrayList<>();
        photoAdapter = new PhotoAdapter(this, imagePaths);
        mission_rec.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        mission_rec.setAdapter(photoAdapter);
        com_title.setText("上传资料");
        com_button.setText("提交");
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
