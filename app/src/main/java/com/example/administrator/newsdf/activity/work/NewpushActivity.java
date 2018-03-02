package com.example.administrator.newsdf.activity.work;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.Bean.PhotoBean;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.joanzapata.iconify.widget.IconTextView;
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
import static com.example.administrator.newsdf.R.id.drawer_layout_list;

/**
 * description: 新增推送任务
 *
 * @author: lx
 * date: 2018/2/6 0006 上午 9:20
 * update: 2018/2/6 0006
 * version:
 */
public class NewpushActivity extends AppCompatActivity {
    private TextView com_button, push_username, wbs_text, reply_button;
    private IconTextView back;
    private LinearLayout newpush_wbs, newpush_user, newpush_name;
    private EditText newpush_check, push_content;
    private String Wbsid, wbsname, userid;
    private Context mContent;
    boolean staus = false;
    private int page = 1;
    private ArrayList<PhotoBean> imagePaths;
    private TaskPhotoAdapter taskAdapter;

    private CircleImageView fab;
    private DrawerLayout drawerLayout;
    private SmartRefreshLayout drawerLayoutSmart;
    private ListView drawerLayoutList;
    private boolean drew = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpush);
        mContent = NewpushActivity.this;
        imagePaths = new ArrayList<>();
        fab = (CircleImageView) findViewById(R.id.fab);
        drawerLayout = (DrawerLayout) findViewById(drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayoutSmart = (SmartRefreshLayout) findViewById(drawerLayout_smart);
        drawerLayoutSmart.setEnableRefresh(false);
        drawerLayoutList = (ListView) findViewById(drawer_layout_list);
        com_button = (TextView) findViewById(R.id.newpush_title);
        reply_button = (TextView) findViewById(R.id.newpush_button);
        //wbs名字
        wbs_text = (TextView) findViewById(R.id.newpush_text);
        //推送人名字
        push_username = (TextView) findViewById(R.id.push_username);
        //选择推送人
        newpush_wbs = (LinearLayout) findViewById(R.id.newpush_wbs);
        //选择推送人
        newpush_user = (LinearLayout) findViewById(R.id.newpush_user);
        //推送任务名字
        newpush_check = (EditText) findViewById(R.id.newpush_check);
        //送内容
        push_content = (EditText) findViewById(R.id.push_content);
        back = (IconTextView) findViewById(R.id.newpush_back);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        com_button.setText("下发任务");
        reply_button.setText("推送");
        Intent intent = getIntent();
        Wbsid = intent.getExtras().getString("wbsID");
        wbsname = intent.getExtras().getString("wbsname");
        wbs_text.setText(wbsname);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        newpush_wbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewpushActivity.this, MmissPushActivity.class);
                intent.putExtra("data", "newpush");
                startActivityForResult(intent, 1);
            }
        });
        reply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Content = push_content.getText().toString();
                if (TextUtils.isEmpty(userid) && TextUtils.isEmpty(Content) && TextUtils.isEmpty(Wbsid)) {
                    Toast.makeText(mContent, "还有未填项", Toast.LENGTH_SHORT).show();
                } else {
                    reply_button.setEnabled(false);
                    Dates.getDialog(NewpushActivity.this, "推送中");
                    if (staus == false) {
                        okgo();
                        staus = true;
                    }
                }

            }
        });
        taskAdapter = new TaskPhotoAdapter(imagePaths, NewpushActivity.this);
        drawerLayoutList.setAdapter(taskAdapter);
        newpush_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewpushActivity.this, MemberActivity.class);
                intent.putExtra("data", "newpush");
                startActivityForResult(intent, 1);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                photoAdm(Wbsid);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });
        photoAdm(Wbsid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是不是Activity的返回，不是就是相机的返回
        if (requestCode == 1 && resultCode == 1) {
            Wbsid = data.getStringExtra("wbsId");
            wbsname = data.getStringExtra("title");
            wbs_text.setText(wbsname);
            page = 1;
            photoAdm(Wbsid);
        } else if (requestCode == 1 && resultCode == 2) {
            String name = data.getStringExtra("name");
            userid = data.getStringExtra("userId");
            push_username.setText(name);
        }
    }

    void okgo() {
        OkGo.post(Request.newPush)
                .params("wbsId", Wbsid)
                .params("leaderId", userid)
                .params("label", newpush_check.getText().toString())
                .params("content", push_content.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String str = jsonObject.getString("msg");
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                finish();
                                Toast.makeText(mContent, str, Toast.LENGTH_SHORT).show();
                            } else {
                                staus = false;
                                Toast.makeText(mContent, str, Toast.LENGTH_SHORT).show();
                            }
                            newpush_check.setText("");
                            reply_button.setEnabled(true);
                            Dates.disDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 查询图册
     */
    private void photoAdm(String string) {
        OkGo.post(Request.Photolist)
                .params("WbsId", string)
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
                                taskAdapter.getData(imagePaths);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (drew) {
                                imagePaths.clear();
                                imagePaths.add(new PhotoBean(Wbsid, "暂无数据", "暂无数据", "暂无数据", "暂无数据"));
                            }
                            taskAdapter.getData(imagePaths);
                            ToastUtils.showShortToast("没有更多数据了！");
                        }

                    }
                });
    }
}

