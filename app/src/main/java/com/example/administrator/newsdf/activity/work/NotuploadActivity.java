package com.example.administrator.newsdf.activity.work;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.Adapter.Adapter;
import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.same.ReplyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

/**
 * description: 未上传资料列表界面
 *
 * @author: lx
 * date: 2018/2/6 0006 上午 9:21
 * update: 2018/2/6 0006
 * version:
 */

public class NotuploadActivity extends AppCompatActivity implements Adapter.IonSlidingViewClickListener {
    private LinearLayout toolbar_add;
    LinearLayout com_back;
    String wbsID;
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    List<Shop> list;
    int pos = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notupload);
        toolbar_add = (LinearLayout) findViewById(R.id.toolbar_add);
        toolbar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotuploadActivity.this, ReplyActivity.class);
                intent.putExtra("position", -1);
                startActivityForResult(intent, 1);
            }
        });
        com_back = (LinearLayout) findViewById(R.id.com_back);
        com_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //设置布局管理器
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        mRecyclerView.setAdapter(mAdapter = new Adapter(this));
        //设置控制Item增删的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        queryDate();

    }

    @Override
    protected void onStart() {
        super.onStart();
        queryDate();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    //更新数据
    private void queryDate() {
        list = new ArrayList<>();
        list = LoveDao.queryLove();
        mAdapter.getData(list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是不是Activity的返回，不是就是相机的返回
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (!list.isEmpty()) {
                queryDate();
            }
        } else if (requestCode == 2 && resultCode == 2) {
//            deleteDate(pos);
            queryDate();
        }

    }

    //删除记录
    public void deleteDate(int pos) {
        if (!list.isEmpty()) {
            LoveDao.deleteLove(list.get(pos).getId());
        }
        mAdapter.closeMenu();
        queryDate();
    }


    /**
     * item的左滑设置
     *
     * @param view
     * @param position
     */
    @Override
    public void onSetBtnCilck(View view, int position) {

    }


    /**
     * item的左滑删除
     *
     * @param view
     * @param position
     */
    @Override
    public void onDeleteBtnCilck(View view, int position) {
        deleteDate(position);
    }

    //点击条目跳转界面
    public void getInt(int position) {
        pos = position;
        Log.i("pos", pos + "");
        Intent intent = new Intent(this, ReplyActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("content", list.get(position).getContent());
        intent.putExtra("wbsname", list.get(position).getName());
        intent.putExtra("id", list.get(position).getWebsid());
        intent.putExtra("Imgpath", list.get(position).getImage_url());
        intent.putExtra("Checkid", list.get(position).getCheckid());
        intent.putExtra("Checkname", list.get(position).getCheckname());
        intent.putExtra(" notup", "notup");
        startActivityForResult(intent, 2);
    }

}
