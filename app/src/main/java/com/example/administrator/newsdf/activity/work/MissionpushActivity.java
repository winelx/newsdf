package com.example.administrator.newsdf.activity.work;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.Bean.PhotoBean;
import com.example.administrator.newsdf.Bean.Push_item;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.work.PushAdapter.PushAdapter;
import com.example.administrator.newsdf.utils.Request;
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


/**
 * 任务推送
 */
public class MissionpushActivity extends AppCompatActivity {
    private TextView title, button;
    private LinearLayout com_img;
    private RelativeLayout tabulation;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private PushAdapter mAdapter;
    int msg = 0;
    int page = 1;
    ArrayList<ArrayList<Push_item>> push;
    ArrayList<String> titlename = null;
    ArrayList<String> ids = new ArrayList<>();
    private Context mContext;
    String id, wbsname;
    private ArrayList<PhotoBean> imagePaths;
    private CircleImageView fab;
    private SmartRefreshLayout smartRefreshLayout;
    private DrawerLayout drawer_layout;
    private ListView drawer_layout_list;
    private TaskPhotoAdapter taskAdapter;
    private boolean drew = true;
    private boolean popwind = true;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionpush);
        mContext = MissionpushActivity.this;
        push = new ArrayList<>();
        imagePaths = new ArrayList<>();
        fab = (CircleImageView) findViewById(R.id.fab);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(drawerLayout_smart);
        smartRefreshLayout.setEnableRefresh(false);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout_list = (ListView) findViewById(R.id.drawer_layout_list);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer_layout.setScrimColor(Color.TRANSPARENT);
        title = (TextView) findViewById(R.id.com_title);
        button = (TextView) findViewById(R.id.com_button);
        com_img = (LinearLayout) findViewById(R.id.com_img);
        com_img.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        title.setText("任务下发");
        button.setBackgroundResource(R.mipmap.toolbar_push);
        tabulation = (RelativeLayout) findViewById(R.id.tabulation);
        Intent intent = getIntent();
        //获取到intent传过来得集合
        titlename = new ArrayList<>();
        try {
            id = intent.getExtras().getString("id");
            wbsname = intent.getExtras().getString("wbsnam");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            ids = intent.getExtras().getStringArrayList("ids");
            titlename = intent.getExtras().getStringArrayList("title");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        initView();
        //列表详情
        tabulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MissionpushActivity.this, TabulationActivity.class);
                intent.putExtra("data", titlename);
                startActivityForResult(intent, 1);
            }
        });
        //新增推送
        com_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View contentView = getPopupWindowContentView();
                mPopupWindow = new PopupWindow(contentView,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
                mPopupWindow.setBackgroundDrawable(new ColorDrawable());

                // 设置好参数之后再show
                // 默认在mButton2的左下角显示
                mPopupWindow.showAsDropDown(com_img);
                backgroundAlpha(0.5f);
                //添加pop窗口关闭事件
                mPopupWindow.setOnDismissListener(new poponDismissListener());

            }
        });
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
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

    private void initView() {
        taskAdapter = new TaskPhotoAdapter(imagePaths, MissionpushActivity.this);
        drawer_layout_list.setAdapter(taskAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tl_tab);
        if (titlename.size() > 3) {
            mTabLayout.setTabMode(0);
        }
        mViewPager = (ViewPager) findViewById(R.id.vp_pager);
        mViewPager.setOffscreenPageLimit(6);
        mAdapter = new PushAdapter(getSupportFragmentManager(), titlename);
        mViewPager.setAdapter(mAdapter);
        mAdapter.getID(id);
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

    //接受activity返回的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            msg = data.getIntExtra("position", 1);
            mViewPager.setCurrentItem(msg);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
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
                                imagePaths.add(new PhotoBean(id, "暂无数据", "暂无数据", "暂无数据", "暂无数据"));
                            }
                            taskAdapter.getData(imagePaths);
                        }

                    }
                });
    }

    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.popup_content_layout;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Click " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                switch (v.getId()) {
                    case R.id.menu_item1:
                        Intent intent = new Intent(MissionpushActivity.this, NewpushActivity.class);
                        intent.putExtra("wbsname", wbsname);
                        intent.putExtra("wbsID", id);
                        //节点名称
                        intent.putExtra("title", "下发任务");
                        startActivity(intent);
                        break;
                    case R.id.menu_item2:

                        break;
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.menu_item1).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.menu_item2).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }
}