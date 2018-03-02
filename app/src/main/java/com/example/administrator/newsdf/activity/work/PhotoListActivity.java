package com.example.administrator.newsdf.activity.work;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.Bean.OrganizationEntity;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.TreeView.Node;
import com.example.administrator.newsdf.TreeView.PhotolistViewAdapter;
import com.example.administrator.newsdf.TreeView.TreeListViewAdapter;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionItem;
import okhttp3.Call;
import okhttp3.Response;

public class PhotoListActivity extends AppCompatActivity {
    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;
    private ListView mTree;
    private PhotolistViewAdapter<OrganizationEntity> mTreeAdapter;
    private TextView com_title;
    private int addPosition;
    private Context mContext;
    String wbsname;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                okgo();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        LinearLayout back = (LinearLayout) findViewById(R.id.com_back);
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("选择图册");
        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.CALL_PHONE, getString(R.string.permission_cus_item_phone), R.drawable.permission_ic_phone));
        HiPermission.create(PhotoListActivity.this)
                .permissions(permissonItems)
                .checkMutiPermission(null);
        mTree = (ListView) findViewById(R.id.wbs_listview);
        mContext = PhotoListActivity.this;
        mTreeDatas = new ArrayList<>();
        addOrganizationList = new ArrayList<>();
        organizationList = new ArrayList<>();
        okgo();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void okgo() {
        OkGo.post(Request.PhotoList)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("OKGo", s);
                        mTreeDatas.clear();
                        getWorkOrganizationList(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    void addOrganiztion(final String id, final boolean iswbs, final boolean isparent, final String type) {
        Dates.getDialogs(PhotoListActivity.this, "请求数据中");
        OkGo.post(Request.PhotoList)
                .params("nodeid", id)
                .params("isDrawingGroup", iswbs)
                .params("isParent", isparent)
                .params("type", type)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
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
        getOrganization(organizationList);
    }

    /**
     * 组织机构
     *
     * @param json 字符串
     * @return 实体
     */
    public ArrayList<OrganizationEntity> parseOrganizationList(String json) {
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
                        organization.setId("");
                    }
                    try {
                        organization.setDepartname(obj.getString("name")); //节点名称
                    } catch (JSONException e) {
                        organization.setDepartname("");
                    }
                    try {
                        organization.setTypes(obj.getString("type")); //组织类型
                    } catch (JSONException e) {
                        organization.setTypes("");
                    }
                    try {
                        organization.setIswbs(obj.getBoolean("iswbs")); //是否swbs
                    } catch (JSONException e) {
                        organization.setIswbs(false);
                    }
                    try {
                        organization.setIsparent(obj.getBoolean("isParent")); //是否是父节点
                    } catch (JSONException e) {
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
                        organization.setIsleaf("");
                    }
                    try {
                        organization.setParentId(obj.getString("parentId")); //组织机构父级节点
                    } catch (JSONException e) {
                        organization.setParentId("");
                    }
                    try {
                        organization.setUsername(obj.getJSONObject("extend").getString("leaderName")); //负责人 //进度
                    } catch (JSONException e) {
                        organization.setUsername("");
                    }

                    try {
                        organization.setNumber(obj.getJSONObject("extend").getString("finish")); //进度
                    } catch (JSONException e) {
                        organization.setNumber("");
                    }
                    try {
                        organization.setUserId(obj.getJSONObject("extend").getString("leaderId")); //负责热ID
                    } catch (JSONException e) {
                        organization.setUserId("");
                    }
                    try {
                        organization.setTitle(obj.getString("title")); //节点层级
                    } catch (JSONException e) {
                        organization.setTitle("");
                    }
                    try {
                        organization.setPhone(obj.getString("title")); //节点层级
                    } catch (JSONException e) {
                        organization.setPhone("");
                    }
                    try {
                        organization.setDrawingGroup(obj.getBoolean("isDrawingGroup"));
                    } catch (JSONException e) {
                        organization.setPhone("");
                    }
                    organizationList.add(organization);
                }
                if (organizationList.size() != 0) {
                }
                return organizationList;
            } catch (JSONException e) {

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
        if (result.indexOf("data") != -1) {
            addOrganizationList = parseOrganizationList(result);
            for (int i = addOrganizationList.size() - 1; i >= 0; i--) {
                String departmentName = addOrganizationList.get(i).getDepartname();
                mTreeAdapter.addExtraNode(addPosition, addOrganizationList.get(i).getId(),
                        addOrganizationList.get(i).getParentId(), departmentName, addOrganizationList.get(i).getIsleaf(),
                        addOrganizationList.get(i).iswbs(),
                        addOrganizationList.get(i).isparent(),
                        addOrganizationList.get(i).getTypes(), addOrganizationList.get(i).getUsername(),
                        addOrganizationList.get(i).getNumber(), addOrganizationList.get(i).getUserId(),
                        addOrganizationList.get(i).getTitle(), addOrganizationList.get(i).getPhone(), addOrganizationList.get(i).isDrawingGroup());
            }
            Dates.disDialog();
        } else {
            Log.i("ss", "不包含");
            Dates.disDialog();
        }
    }

    private void getOrganization(ArrayList<OrganizationEntity> organizationList) {
        if (organizationList != null) {
            for (OrganizationEntity entity : organizationList) {
                String departmentName = entity.getDepartname();
                OrganizationEntity bean = new OrganizationEntity(entity.getId(), entity.getParentId(),
                        departmentName, entity.getIsleaf(), entity.iswbs(),
                        entity.isparent(), entity.getTypes(), entity.getUsername(),
                        entity.getNumber(), entity.getUserId(), entity.getTitle(), entity.getPhone(), entity.isDrawingGroup());
                mTreeDatas.add(bean);
            }
            try {
                mTreeAdapter = new PhotolistViewAdapter(mTree, this,
                        mTreeDatas, 0);
                mTree.setAdapter(mTreeAdapter);
                initEvent(organizationList);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    private void initEvent(ArrayList<OrganizationEntity> organizationList) {
        mTreeAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(com.example.administrator.newsdf.TreeView.Node node, int position) {
                if (node.isLeaf()) {
                } else {
                    if (node.getChildren().size() == 0) {
                        addOrganizationList.clear();
                        addPosition = position;
                        if (node.isperent()) {
                            addOrganiztion(node.getId(), node.isDrawingGroup(), node.isperent(), node.getType());
                        } else {

                        }

                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void switchAct(Node node) {
        Log.i("sss", node.isDrawingGroup() + "");
        if (node.isDrawingGroup() == true) {
            Intent intent1 = new Intent(mContext, ListPhActivity.class);
            intent1.putExtra("groupId", node.getId());
            intent1.putExtra("title", node.getName());
            startActivity(intent1);
        } else {

        }


    }


}
