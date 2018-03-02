package com.example.administrator.newsdf.activity.work;


import android.app.Activity;
import android.content.Context;

import android.os.Bundle;

import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.newsdf.Bean.OrganizationEntity;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.TreeView.Node;
import com.example.administrator.newsdf.TreeView.SimpleTreeListViewAdapter;
import com.example.administrator.newsdf.TreeView.TreeListViewAdapter;

import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class OrganizationActivity extends Activity {
    private TextView back, title, search;

    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;

    private ListView mTree;
    private SimpleTreeListViewAdapter<OrganizationEntity> mTreeAdapter;

    private int addPosition;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wbs);
        mContext = OrganizationActivity.this;
        mTreeDatas = new ArrayList<>();
        organizationList = new ArrayList<>();
        initView();
        OkGo.post(Request.WBSTress)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        getWorkOrganizationList(s);
                    }
                });
    }

    private void initView() {
        // TODO Auto-generated method stub
        mTree = (ListView) findViewById(R.id.wbs_listview);
    }

    void addOrganiztion(String id, boolean iswbs, boolean isparent, String type) {
        OkGo.post(Request.WBSTress)
                .params("id", id)
                .params("iswbs", iswbs)
                .params("isparent", isparent)
                .params("type", type)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        Log.i("sssss", result);
                        addOrganizationList(result);
                    }
                });

    }


    /**
     * 解析组织机构对象
     *
     * @param result
     * @return
     */
    private void getWorkOrganizationList(String result) {
        organizationList = parseOrganizationList(result);
        getOrganization();
    }


    /**
     * 组织机构
     *
     * @param json 字符串
     * @return 实体
     */
    public static ArrayList<OrganizationEntity> parseOrganizationList(String json) {
        if (json == null) {
            return null;
        } else {
            ArrayList<OrganizationEntity> organizationList = new ArrayList<OrganizationEntity>();
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    OrganizationEntity organization = new OrganizationEntity();
                    try {
                        organization.setId(obj.getString("id")); //节点id
                    } catch (JSONException e) {
                        // TODO: handle exception
                        organization.setId("");
                    }
                    try {
                        organization.setDepartname(obj.getString("name")); //节点名称
                    } catch (JSONException e) {
                        // TODO: handle exception
                        organization.setDepartname("");
                    }
                    try {
                        organization.setTypes(obj.getString("type")); //组织类型
                    } catch (JSONException e) {
                        // TODO: handle exception
                        organization.setTypes("");
                    }
                    try {
                        organization.setIswbs(obj.getBoolean("iswbs")); //是否swbs
                    } catch (JSONException e) {
                        // TODO: handle exception
                        organization.setIswbs(false);
                    }
                    try {
                        organization.setIsparent(obj.getBoolean("isParent")); //是否是父节点
                    } catch (JSONException e) {
                        // TODO: handle exception
                        organization.setIsparent(false);
                    }
                    try {
                        boolean isParentFlag = obj.getBoolean("isParent");
                        if (isParentFlag) {
                            organization.setIsleaf("0"); //不是叶子节点
                        } else {
                            organization.setIsleaf("1"); //是叶子节点
                        }
                    } catch (JSONException e) {
                        // TODO: handle exception
                        organization.setIsleaf("");
                    }
                    try {
                        organization.setParentId(obj.getString("parentId")); //组织机构父级节点
                    } catch (JSONException e) {
                        // TODO: handle exception
                        organization.setParentId("");
                    }
                    organizationList.add(organization);
                }
                return organizationList;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 解析SoapObject对象
     *
     * @return
     */
    private void addOrganizationList(String result) {
        addOrganizationList = parseOrganizationList(result);
        for (int i = addOrganizationList.size() - 1; i >= 0; i--) {
            String departmentName = addOrganizationList.get(i).getDepartname();
            mTreeAdapter.addExtraNode(addPosition, addOrganizationList.get(i).getId(),
                    addOrganizationList.get(i).getParentId(),
                    departmentName, addOrganizationList.get(i).getIsleaf(),
                    addOrganizationList.get(i).iswbs(),
                    addOrganizationList.get(i).isparent(),
                    addOrganizationList.get(i).getTypes(), addOrganizationList.get(i).getUsername(),
                    addOrganizationList.get(i).getNumber(), addOrganizationList.get(i).getUserId(),
                    addOrganizationList.get(i).getTitle(), addOrganizationList.get(i).getPhone(), addOrganizationList.get(i).isDrawingGroup());
        }
    }

    private void getOrganization() {
        // TODO Auto-generated method stub
        for (OrganizationEntity entity : organizationList) {
            String departmentName = entity.getDepartname();
            OrganizationEntity bean = new OrganizationEntity(entity.getId(), entity.getParentId(),
                    departmentName, entity.getIsleaf(), entity.iswbs(),
                    entity.isparent(), entity.getTypes(), entity.getUsername(),
                    entity.getNumber(), entity.getUserId(), entity.getTitle(), entity.getPhone(), entity.isDrawingGroup());
            mTreeDatas.add(bean);
        }
        try {
            mTreeAdapter = new SimpleTreeListViewAdapter<OrganizationEntity>(mTree, this,
                    mTreeDatas, 0);
            mTree.setAdapter(mTreeAdapter);
            initEvent();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void initEvent() {
        mTreeAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {
                if (node.isLeaf()) {
                    Toast.makeText(mContext, "fou", Toast.LENGTH_SHORT).show();
                } else {
                    if (node.getChildren().size() == 0) {
                        addOrganizationList = new ArrayList<OrganizationEntity>();
                        addOrganizationList.clear();
                        addPosition = position;
                        addOrganiztion(node.getId(), node.iswbs(), node.isperent(), node.getType());
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
