package com.example.administrator.newsdf.activity.work;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.newsdf.Bean.OrganizationEntity;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.TreeView.Node;
import com.example.administrator.newsdf.TreeView.SimpleTreeListViewAdapter;
import com.example.administrator.newsdf.TreeView.TreeListViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class MissioncheckItemActivity extends AppCompatActivity {
    private ListView mTree;
    private SimpleTreeListViewAdapter<OrganizationEntity> mAdapter;
    private List<OrganizationEntity> mDatas2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missioncheck_item);
        mTree = (ListView) findViewById(R.id.activity_check_itek);
        initDatas();
        try {
            mAdapter = new SimpleTreeListViewAdapter<OrganizationEntity>(mTree, this,
                    mDatas2, 0);
            mTree.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        initEvent();
    }

    private void initEvent() {
        mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {
                if (node.isLeaf()) {
                    Toast.makeText(MissioncheckItemActivity.this, node.getName(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initDatas() {

        mDatas2 = new ArrayList<OrganizationEntity>();

    }

}
