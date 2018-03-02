package com.example.administrator.newsdf.activity.work;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.Bean.OrganizationEntity;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.TreeView.Node;
import com.example.administrator.newsdf.TreeView.SimpleTreeListViewAdapter;
import com.example.administrator.newsdf.TreeView.TreeListViewAdapter;
import com.example.administrator.newsdf.camera.ToastUtils;
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

/**
 * @author lx
 *  任务维护的wbs树
 */

public class OrganiwbsActivity extends Activity {
    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;
    private ListView mTree;
    private SimpleTreeListViewAdapter<OrganizationEntity> mTreeAdapter;
    private TextView com_title;
    private int addPosition;
    private Context mContext;
    private SmartRefreshLayout refreshLayout;
    String wbsname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wbs);
        mContext = OrganiwbsActivity.this;
        refreshLayout = findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                okgo();
                //传入false表示刷新失败
                refreshlayout.finishRefresh(2000);
            }
        });
        LinearLayout back = (LinearLayout) findViewById(R.id.com_back);
        com_title = findViewById(R.id.com_title);
        com_title.setText("选择WBS节点");
        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.CALL_PHONE,
                getString(R.string.permission_cus_item_phone), R.drawable.permission_ic_phone));
        HiPermission.create(OrganiwbsActivity.this)
                .permissions(permissonItems)
                .checkMutiPermission(null);
        mTree = (ListView) findViewById(R.id.wbs_listview);
        mContext = OrganiwbsActivity.this;
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
        OkGo.post(Request.WBSTress)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mTreeDatas.clear();
                        getWorkOrganizationList(s);
                    }
                });
    }

    void addOrganiztion(final String id, final boolean iswbs,
                        final boolean isparent, String type) {
        Dates.getDialogs(OrganiwbsActivity.this, "请求数据中");
        OkGo.post(Request.WBSTress)
                .params("nodeid", id)
                .params("iswbs", iswbs)
                .params("isparent", isparent)
                .params("type", type)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        addOrganizationList(result);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
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
                        //节点id
                        organization.setId(obj.getString("id"));
                    } catch (JSONException e) {

                        organization.setId("");
                    }
                    try {
                        //节点名称
                        organization.setDepartname(obj.getString("name"));
                    } catch (JSONException e) {

                        organization.setDepartname("");
                    }
                    try {
                        //组织类型
                        organization.setTypes(obj.getString("type"));
                    } catch (JSONException e) {
                        organization.setTypes("");
                    }
                    try {
                        //是否swbs
                        organization.setIswbs(obj.getBoolean("iswbs"));
                    } catch (JSONException e) {

                        organization.setIswbs(false);
                    }
                    try {
                        //是否是父节点
                        organization.setIsparent(obj.getBoolean("isParent"));
                    } catch (JSONException e) {

                        organization.setIsparent(false);
                    }
                    try {
                        boolean isParentFlag = obj.getBoolean("isParent");
                        if (isParentFlag) {
                            //不是叶子节点
                            organization.setIsleaf("0");
                        } else {
                            //是叶子节点
                            organization.setIsleaf("1");
                        }
                    } catch (JSONException e) {

                        organization.setIsleaf("");
                    }
                    try {
                        //组织机构父级节点
                        organization.setParentId(obj.getString("parentId"));
                    } catch (JSONException e) {

                        organization.setParentId("");
                    }

                    try {
                        //负责人 //进度
                        organization.setUsername(obj.getJSONObject("extend").getString("leaderName"));
                    } catch (JSONException e) {
                        organization.setUsername("");
                    }
                    try {
                        //进度
                        organization.setNumber(obj.getJSONObject("extend").getString("finish"));
                    } catch (JSONException e) {
                        organization.setNumber("");
                    }
                    try {
                        //负责热ID
                        organization.setUserId(obj.getJSONObject("extend").getString("leaderId"));
                    } catch (JSONException e) {
                        organization.setUserId("");
                    }
                    try {
                        //节点层级
                        organization.setTitle(obj.getString("title"));
                    } catch (JSONException e) {
                        organization.setTitle("");
                    }
                    try {
                        organization.setPhone(obj.getString("title"));
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
                mTreeAdapter = new SimpleTreeListViewAdapter<OrganizationEntity>(mTree, this,
                        mTreeDatas, 0);
                mTree.setAdapter(mTreeAdapter);
                initEvent(organizationList);
                Dates.disDialog();
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
                            addOrganiztion(node.getId(), node.iswbs(), node.isperent(), node.getType());
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

    public void switchAct(Node node, final String name) {
        wbsname = name;
        if (node.iswbs() != false) {
            getOko(node.getId(), node.getTitle());
        } else {
//            Toast.makeText(mContext, "不是wbs,无法跳转", Toast.LENGTH_SHORT).show();
        }
    }

    void getOko(final String str, final String title) {
        final ArrayList<String> namess = new ArrayList<>();
        final ArrayList<String> ids = new ArrayList<>();
        final ArrayList<String> titlename = new ArrayList<>();
        Dates.getDialog(OrganiwbsActivity.this, "请求数据中");
        OkGo.post(Request.WbsTaskGroup)
                .params("wbsId", str)
                .params("isNeedTotal", "true")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.indexOf("data") != -1) {
                            System.out.println("有数据");
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String id = json.getString("id");
                                    String name = json.getString("detectionName");
                                    String totalNum = json.getString("totalNum");
                                    namess.add(name + "(" + totalNum + ")");
                                    ids.add(id);
                                    titlename.add(name);
                                }
                                Intent intent = new Intent(OrganiwbsActivity.this, TenanceviewActivity.class);
                                //加了任务数量的检查点
                                intent.putExtra("name", namess);
                                //检查点ID
                                intent.putExtra("ids", ids);
                                //检查点名称
                                intent.putExtra("title", titlename);
                                //节点ID
                                intent.putExtra("id", str);
                                //节点路径
                                intent.putExtra("wbspath", title);
                                startActivity(intent);
                                Dates.disDialog();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showShortToast("该节点未启动");
                            Intent intent = new Intent(OrganiwbsActivity.this, TenanceviewActivity.class);
                            //加了任务数量的检查点
                            intent.putExtra("name", namess);
                            //检查点ID
                            intent.putExtra("ids", ids);
                            //检查点名称
                            intent.putExtra("title", titlename);
                            //节点ID
                            intent.putExtra("id", str);
                            //节点路径
                            intent.putExtra("wbspath", title);
                            startActivity(intent);
                            Dates.disDialog();
                        }

                    }
                });
    }

    public void getphone(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
