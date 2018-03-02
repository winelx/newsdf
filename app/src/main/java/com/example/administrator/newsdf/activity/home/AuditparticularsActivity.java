package com.example.administrator.newsdf.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.Aduio_comm;
import com.example.administrator.newsdf.Adapter.Aduio_content;
import com.example.administrator.newsdf.Adapter.Aduio_data;
import com.example.administrator.newsdf.Adapter.AudioAdapter;
import com.example.administrator.newsdf.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.Bean.PhotoBean;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.same.DirectlyreplyActivity;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.R.id.drawerLayout_smart;
import static com.example.administrator.newsdf.R.id.drawer_layout;


/**
 * description: 审核详情 完成
 *
 * @author: lx
 * date: 2018/2/6 0006 上午 9:25
 * update: 2018/2/6 0006
 * version:
 */
public class AuditparticularsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayout linearLayout;
    private AudioAdapter mAdapter;
    private String id, intent_back;
    private ArrayList<Aduio_content> contents;
    private ArrayList<Aduio_data> aduio_datas;
    private ArrayList<Aduio_comm> aduio_comms;
    private Context mContext;
    private TextView wbsnam, com_title, wbspath, com_button;
    private SPUtils spUtils;
    private PopupWindow popupWindow;
    private String wtMainid = null, status, wbsid;
    private String wbsName = null, usernma;
    private SwipeRefreshLayout mSwipeLayout;
    private CircleImageView fab;
    private ArrayList<PhotoBean> imagePaths;
    private int page = 1;
    LinearLayout back;
    TaskPhotoAdapter taskPhotoAdapter;
    DrawerLayout drawerLayout;
    SmartRefreshLayout drawerLayoutSmart;
    ListView drawerLayoutList;
    private boolean drew;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_auditparticulars);
        mContext = AuditparticularsActivity.this;
        usernma = SPUtils.getString(mContext, "staffName", null);
        Intent intent = getIntent();
        try {
            id = intent.getExtras().getString("frag_id");
            status = intent.getExtras().getString("status");
            wbsid = intent.getExtras().getString("wbsid");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        drawerLayout = (DrawerLayout) findViewById(drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayoutSmart = (SmartRefreshLayout) findViewById(drawerLayout_smart);
        drawerLayoutList = (ListView) findViewById(R.id.drawer_layout_list);
        fab = (CircleImageView) findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.handover_status_recycler);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_ly);
        //得到跳转到该Activity的Intent对象
        contents = new ArrayList<>();
        aduio_datas = new ArrayList<>();
        aduio_comms = new ArrayList<>();
        imagePaths = new ArrayList<>();
        //侧滑栏关闭
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //关闭下拉刷新
        drawerLayoutSmart.setEnableRefresh(false);
        //获取到intent传过来得集合
        com_title = (TextView) findViewById(R.id.audio_com_title);
        com_title.setText("任务详情");
        wbspath = (TextView) findViewById(R.id.wbspath);
        back = (LinearLayout) findViewById(R.id.adui_com_back);
        com_button = (TextView) findViewById(R.id.audio_com_button);
        mAdapter = new AudioAdapter(mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        //添加分割线
        mAdapter = new AudioAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        switch (status) {
            case "one":
                okgoone(id);
                break;
            case "two":
                okgo(id);
                break;
            default:
                break;
        }

        taskPhotoAdapter = new TaskPhotoAdapter(imagePaths, AuditparticularsActivity.this);
        drawerLayoutList.setAdapter(taskPhotoAdapter);
        com_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AuditparticularsActivity.this, DirectlyreplyActivity.class);
                intent1.putExtra("id", id);
                startActivityForResult(intent1, 1);
            }
        });
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //刷新
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                okgo(id);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                drew = true;
                photoAdm(wbsid);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        /**
         *    侧拉listview上拉加载
         */
        drawerLayoutSmart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                photoAdm(id);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });
    }

    public void getpinL(String content) {
        String name = SPUtils.getString(mContext, "staffName", "");
        aduio_comms.add(0, new Aduio_comm(null, null, name, null, wtMainid, null, "4",
                content, Dates.getDate()));
        mAdapter.getmListB(aduio_comms);
    }

    public String getId() {
        return wtMainid;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return true;
    }

    //未完成数据
    private void okgoone(String ids) {
        OkGo.post(Request.Detail)
                .params("wbsTaskId", ids)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("wbsTaskId",s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONObject wtMain = jsonObject1.getJSONObject("wtMain");
                            JSONObject createBy = wtMain.getJSONObject("createBy");
                            //任务详情
                            try {
                                wbsName = wtMain.getString("wbsName");
                            } catch (JSONException e) {
                                wbsName = "";
                            }
                            try {
                                //唯一标识
                                wtMainid = wtMain.getString("id");
                            } catch (JSONException e) {

                                wtMainid = "";
                            }
                            String name;
                            try {
                                ///检查点
                                name = wtMain.getString("name");
                            } catch (JSONException e) {

                                name = "";
                            }
                            String status;
                            //状态
                            try {
                                status = wtMain.getString("status");
                            } catch (JSONException e) {
                                status = "";
                            }
                            String content;
                            //推送内容
                            try {
                                content = wtMain.getString("content");
                            } catch (JSONException e) {

                                content = "";
                            }
                            String leaderName = null;
                            //负责人
                            try {
                                leaderName = wtMain.getString("leaderName");
                            } catch (JSONException e) {

                                leaderName = "";
                            }
                            String leaderId = null;
                            //负责人ID
                            try {
                                leaderId = wtMain.getString("leaderId");
                            } catch (JSONException e) {

                                leaderId = "";
                            }
                            //是否已读
                            String isread = null;
                            try {
                                isread = wtMain.getString("isread");
                            } catch (JSONException e) {

                                leaderId = "";
                            }
                            //创建人ID  (路径：wtMain –> createBy -> id)
                            String createByUserID;
                            try {
                                createByUserID = createBy.getString("id");
                            } catch (JSONException e) {

                                createByUserID = "";
                            }
                            //是否被打回过
                            String iscallback;
                            try {
                                iscallback = wtMain.getString("iscallback");
                            } catch (JSONException e) {
                                iscallback = "";
                            }
                            //更新时间
                            String createDate = wtMain.getString("createDate");
                            //wbsname
                            //转交id
                            String changeId = null;
                            String backdata;
                            try {
                                backdata = wtMain.getString("updateDate");
                            } catch (JSONException e) {
                                //打回说明
                                backdata = ("");
                            }

                            contents.add(new Aduio_content(wtMainid, name, status, content, leaderName, leaderId, isread,
                                    createByUserID, iscallback, createDate, wbsName, changeId, backdata));
                            if (usernma.equals(wtMain.getString("leaderName"))) {
                                com_button.setText("回复");
                            }
                            wbspath.setText(wbsName);
                            mAdapter.setmBanner(contents);
                            mAdapter.getmListA(aduio_datas);
                            mAdapter.getmListB(aduio_comms);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    //完成详细数据
    private void okgo(final String id) {
        mSwipeLayout.setRefreshing(false);
        OkGo.post(Request.Detail)
                .params("wbsTaskId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("audio", s);
                        //任务详情
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                contents.clear();
                                aduio_datas.clear();
                                aduio_comms.clear();
                            }
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject wtMain = data.getJSONObject("wtMain");
                            JSONObject createBy = wtMain.getJSONObject("createBy");
                            JSONArray subWbsTaskMains = data.getJSONArray("subWbsTaskMains");
                            JSONObject uploadUser = wtMain.getJSONObject("uploadUser");
                            JSONArray comments = data.getJSONArray("comments");
                            //评论人头像
                            String userpath;
                            try {
                                String path = uploadUser.getString("portrait");
                                userpath = Request.networks + path;
                            } catch (JSONException e) {

                                userpath = "";
                            }
                            //任务详情
                            try {
                                wbsName = wtMain.getString("wbsName");
                            } catch (JSONException e) {

                                wbsName = "";
                            }
                            try {
                                //唯一标识
                                wtMainid = wtMain.getString("id");
                            } catch (JSONException e) {

                                wtMainid = "";
                            }
                            String name;
                            try {
                                ///检查点
                                name = wtMain.getString("name");
                            } catch (JSONException e) {

                                name = "";
                            }
                            String status;
                            //状态
                            try {
                                status = wtMain.getString("status");
                            } catch (JSONException e) {

                                status = "";
                            }
                            String content;
                            //推送内容
                            try {
                                content = wtMain.getString("content");
                            } catch (JSONException e) {

                                content = "";
                            }
                            String leaderName = null;
                            //负责人
                            try {
                                leaderName = wtMain.getString("leaderName");
                            } catch (JSONException e) {

                                leaderName = "";
                            }
                            String leaderId = null;
                            //负责人ID
                            try {
                                leaderId = wtMain.getString("leaderId");
                            } catch (JSONException e) {

                                leaderId = "";
                            }
                            //是否已读
                            String isread = null;
                            try {
                                isread = wtMain.getString("isread");
                            } catch (JSONException e) {

                                leaderId = "";
                            }
                            //创建人ID  (路径：wtMain –> createBy -> id)
                            String createByUserID;
                            try {
                                createByUserID = createBy.getString("id");
                            } catch (JSONException e) {

                                createByUserID = "";
                            }
                            //是否被打回过
                            String iscallback;
                            try {
                                iscallback = wtMain.getString("iscallback");
                            } catch (JSONException e) {
                                iscallback = "";
                            }
                            //更新时间
                            String createDate = wtMain.getString("createDate");
                            //wbsname
                            wbsName = wtMain.getString("wbsName");
                            //转交id
                            String changeId = null;
                            String backdata;
                            try {
                                backdata = wtMain.getString("updateDate");
                            } catch (JSONException e) {
                                //打回说明
                                backdata = ("");
                            }

                            contents.add(new Aduio_content(wtMainid, name, status, content,
                                    leaderName, leaderId, isread,
                                    createByUserID, iscallback, createDate, wbsName, changeId, backdata));

                            for (int i = 0; i < subWbsTaskMains.length(); i++) {
                                JSONObject Sub = subWbsTaskMains.getJSONObject(i);
                                JSONObject upload = Sub.getJSONObject("uploadUser");
                                String replyID, uploadId, replyUserName, replyUserHeaderURL,
                                        Sub_name, Sub_wbsName,
                                        uploadContent, updateDate, uploadAddr, Sub_leaderName,
                                        Sub_leaderId, Sub_iscallback, callbackContent;
                                JSONArray hments = new JSONArray();
                                try {
                                    hments = Sub.getJSONArray("attachments");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //  (回复详情列表)
                                try {
                                    //唯一标识
                                    replyID = Sub.getString("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    replyID = "";
                                }
                                try {
                                    //上传人ID
                                    uploadId = Sub.getString("leaderId");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    uploadId = "";
                                }
                                try {
                                    // 上传人姓名 （路径：subWbsTaskMains  -> uploadUser -> realname）
                                    replyUserName = upload.getString("realname");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    replyUserName = "";
                                }
                                try {
                                    replyUserHeaderURL = upload.getString("portrait");
                                } catch (JSONException e) {
                                    replyUserHeaderURL = "";
                                }
                                try {
                                    //检查点
                                    Sub_name = Sub.getString("name");
                                } catch (JSONException e) {
                                    Sub_name = "";
                                }

                                try {
                                    //wbsName
                                    Sub_wbsName = Sub.getString("wbsName");
                                } catch (JSONException e) {
                                    Sub_wbsName = "";
                                }

                                try {
                                    //上传内容说明
                                    uploadContent = Sub.getString("uploadContent");
                                } catch (JSONException e) {
                                    uploadContent = "";
                                }
                                try {
                                    //上传时间
                                    updateDate = Sub.getString("updateDate");
                                } catch (JSONException e) {
                                    updateDate = "";
                                }
                                try {
                                    //上传地点
                                    uploadAddr = Sub.getString("uploadAddr");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    uploadAddr = "";
                                }
                                try {
                                    //任务负责人人
                                    Sub_leaderName = Sub.getString("leaderName");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Sub_leaderName = "";
                                }
                                try {
                                    //任务负责人id
                                    Sub_leaderId = Sub.getString("leaderId");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Sub_leaderId = "";
                                }
                                try {
                                    //是否被打回
                                    Sub_iscallback = Sub.getString("iscallback");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Sub_iscallback = "";
                                }
                                try {
                                    //打回说明
                                    callbackContent = Sub.getString("callbackContent");
                                } catch (JSONException e) {
//打回说明
                                    callbackContent = "";
                                }
                                String callbackTime;
                                try {
                                    //打回说明
                                    callbackTime = Sub.getString("callbackTime");
                                } catch (JSONException e) {
//打回说明
                                    callbackTime = ("");
                                }
                                String callbackId;
                                try {//打回人ID
                                    callbackId = Sub.getString("callbackId");
                                } catch (JSONException e) {
                                    //打回说明
                                    callbackId = "";
                                }
                                String userimage;
                                try {
                                    String path = uploadUser.getString("portrait");
                                    userimage = Request.networks + path;
                                } catch (JSONException e) {
                                    userimage = "";
                                }
                                ArrayList<String> attachments = new ArrayList<>();
                                if (hments.length() > 0) {
                                    for (int j = 0; j < hments.length(); j++) {
                                        JSONObject json = hments.getJSONObject(j);
                                        String path = json.getString("filepath");
                                        attachments.add(Request.networks + path);
                                    }
                                }
                                aduio_datas.add(new Aduio_data(replyID, uploadId, replyUserName, replyUserHeaderURL, Sub_name,
                                        Sub_wbsName, uploadContent, updateDate, uploadAddr, Sub_leaderName, Sub_leaderId, Sub_iscallback,
                                        callbackContent, callbackTime, callbackId, attachments, comments.length() + "", userimage));
                            }

                            for (int i = 0; i < comments.length(); i++) {
                                JSONObject json = comments.getJSONObject(i);
                                JSONObject user = json.getJSONObject("user");
                                //回复评论列表
                                //唯一标识
                                String comments_id = json.getString("id");
                                //回复人ID
                                String replyId = json.getString("replyId");
                                //回复人姓名(路径：comments –> user -> realname)
                                String realname = user.getString("realname");
                                String portrait;
                                try {
                                    portrait = user.getString("portrait");
                                } catch (JSONException e) {
                                    portrait = "";
                                }
                                //回复人头像(路径：comments –> user -> portrait)
                                String taskId = null;
                                String comments_status = json.getString("status");
                                String statusName = null;
                                //Pinglun内容说明
                                String comments_content = json.getString("content");
                                //评论时间
                                String replyTime = json.getString("replyTime");
                                aduio_comms.add(0, new Aduio_comm(comments_id, replyId, realname, portrait, taskId, comments_status, statusName,
                                        comments_content, replyTime));
                            }

                            mAdapter.setmBanner(contents);
                            mAdapter.getmListA(aduio_datas);
                            mAdapter.getmListB(aduio_comms);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        wbspath.setText(wbsName);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String id = data.getStringExtra("frag_id");
            com_button.setVisibility(View.GONE);

            okgo(id);
        }
    }


    /**
     * 查询图册
     */
    private void photoAdm(final String str) {
        OkGo.post(Request.Photolist)
                .params("WbsId", str)
                .params("page", page)
                .params("rows", 5)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.indexOf("data") != -1) {
                            if (drew) {
                                imagePaths.clear();
                            }

                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String id = (String) json.get("id");
                                    String filePath = (String) json.get("filePath");
                                    String drawingNumber = (String) json.get("drawingNumber");
                                    String drawingName = (String) json.get("drawingName");
                                    String drawingGroupName = (String) json.get("drawingGroupName");
                                    filePath = Request.networks + filePath;
                                    imagePaths.add(new PhotoBean(id, filePath, drawingNumber, drawingName, drawingGroupName));
                                }
                                taskPhotoAdapter.getData(imagePaths);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (drew) {
                                imagePaths.clear();
                                imagePaths.add(new PhotoBean(id, "暂无数据", "暂无数据", "暂无数据", "暂无数据"));
                            }
                            taskPhotoAdapter.getData(imagePaths);
                        }
                    }
                });
    }

}
