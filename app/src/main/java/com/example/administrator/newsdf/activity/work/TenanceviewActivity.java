package com.example.administrator.newsdf.activity.work;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.Bean.PhotoBean;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.same.ReplysActivity;
import com.example.administrator.newsdf.activity.work.Adapter.TabAdapter;
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


/**
 * description: //任务维护——列表查看
 *
 * @author lx
 *         date: 2018/2/8 0008 下午 5:01
 *         update: 2018/2/8 0008
 *         version:
 */
public class TenanceviewActivity extends AppCompatActivity {
    private String TAG = "TenanceviewActivity";
    private Context mContext;
    private ViewPager mViewPager;
    private IconTextView com_back;
    private TabLayout mTabLayout;
    private TabAdapter mAdapter;
    private RelativeLayout tabulation;
    private TextView title;
    private LinearLayout com_img;
    private ArrayList<String> ids, names, titlename;
    int msg = 0;
    int page = 1;
    String id, wbspath;
    private CircleImageView fab;
    private SmartRefreshLayout drawerLayout_smart;
    private DrawerLayout drawer_layout;
    private ArrayList<PhotoBean> imagePaths;
    private TaskPhotoAdapter taskAdapter;
    private ListView drawer_layout_list;
    private boolean drew = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionmte);
        Log.i("left", "onCreate");
        mContext = TenanceviewActivity.this;
        //ws得到跳转到该Activity的Intent对象
        Intent intent = getIntent();
        //获取到intent传过来得集合
        names = new ArrayList<>();
        ids = new ArrayList<>();
        titlename = new ArrayList<>();
        imagePaths = new ArrayList<>();
        try {
            //加上检查数量的检查点
            names = intent.getExtras().getStringArrayList("name");
            ids = intent.getExtras().getStringArrayList("ids");
            titlename = intent.getExtras().getStringArrayList("title");
            id = intent.getExtras().getString("id");

            id = intent.getExtras().getString("id");
            //节点路径
            wbspath = intent.getExtras().getString("wbspath");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        drawer_layout_list = (ListView) findViewById(R.id.drawer_layout_list);
        drawerLayout_smart = (SmartRefreshLayout) findViewById(R.id.drawerLayout_smart);
        drawerLayout_smart.setEnableRefresh(false);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        fab = (CircleImageView) findViewById(R.id.fab);
        com_img = (LinearLayout) findViewById(R.id.tenac_img);
        tabulation = (RelativeLayout) findViewById(R.id.tabulation);
        title = (TextView) findViewById(R.id.com_title);
        com_back = (IconTextView) findViewById(R.id.com_back);
        title.setText("任务管理");
        taskAdapter = new TaskPhotoAdapter(imagePaths, TenanceviewActivity.this);
        drawer_layout.setScrimColor(Color.TRANSPARENT);
        drawer_layout_list.setAdapter(taskAdapter);
        tabulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TenanceviewActivity.this, TabulationActivity.class);
                intent.putExtra("data", titlename);
                startActivityForResult(intent, 1);
            }
        });
        com_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TenanceviewActivity.this, ReplysActivity.class);
                intent.putExtra("position", -1);
                //节点名称
                intent.putExtra("title", "我很主动");
                //节点名称
                intent.putExtra("wbsname", wbspath);
                //检查点列表
                intent.putStringArrayListExtra("list", titlename);
                //检查点列表
                intent.putStringArrayListExtra("ids", ids);
                //节点ID
                intent.putExtra("id", id);
                startActivityForResult(intent, 1);
            }
        });
        com_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                drew = true;
                photoAdm(id);
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });
        /**
         *    侧拉listview上拉加载
         */
        drawerLayout_smart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                photoAdm(id);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });
        initView();
    }


    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tl_tab);
        if (names.size() > 3) {
            mTabLayout.setTabMode(0);
        }
        mViewPager = (ViewPager) findViewById(R.id.vp_pager);
        mViewPager.setOffscreenPageLimit(6);
        mAdapter = new TabAdapter(getSupportFragmentManager(), names);
        mAdapter.getAdate(ids, id);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//  那我们如果真的需要监听tab的点击或者ViewPager的切换,则需要手动配置ViewPager的切换,例如:
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换ViewPager
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    ArrayList<String> replly = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //新增任务返回处理
        if (requestCode == 1 && resultCode == RESULT_OK) {
            msg = data.getIntExtra("position", 1);
            replly = data.getStringArrayListExtra("name");
            mViewPager.setCurrentItem(msg);
            mAdapter.getData(replly);
            //点击tab返回的处理
        } else if (requestCode == 1 && resultCode == 2) {
            msg = data.getIntExtra("position", 1);
            mViewPager.setCurrentItem(msg);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

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
                            if (drew = true) {
                                imagePaths.clear();
                                imagePaths.add(new PhotoBean(id, "暂无数据", "暂无数据", "暂无数据", "暂无数据"));
                            }
                            taskAdapter.getData(imagePaths);

                        }

                    }
                });
    }


}
