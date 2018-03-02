package com.example.administrator.newsdf.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.Adapter.Imageloaders;
import com.example.administrator.newsdf.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.Bean.Inface_all_item;
import com.example.administrator.newsdf.Bean.PhotoBean;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.work.MmissPushActivity;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.post;

/**
 * description:
 *
 * @author: lx
 * date: 2018/2/6 0006 上午 9:43
 * update: 2018/2/6 0006
 * version:
 */
public class ListreadActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private ListView uslistView;
    private Imageloaders mAdapter = null;
    private TaskPhotoAdapter taskAdapter;
    private TextView Titlew, intent_loading_text;
    private PopupWindow mPopWindow;
    private EditText search_editext;
    private String id, wbsid, intent_back, name = "";
    private LinearLayout imageView, intent_loading;
    private String status = "3";
    //判断是否刷新还是上拉加载
    private String notall = "all";
    private CircleImageView fab;
    private List<Inface_all_item> Alldata;
    private LinearLayout listerad_nonumber;
    private SmartRefreshLayout refreshLayout, drawerLayout_smart;
    private boolean swip = false;
    //图册
    private ArrayList<PhotoBean> imagePaths;
    private PopupWindow popupWindow;
    private PopupWindow popViewWindow;
    private DrawerLayout drawer_layout;
    ArrayList<String> paths;
    private ListView drawer_layout_list;
    boolean popwind = false;
    private int s = 1;
    int G_whit;
    int page = 1;
    private boolean drew = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtread);
        Dates.getDialog(ListreadActivity.this, "请求数据中...");
        mContext = getApplicationContext();
        int whit = Dates.getScreenHeight(mContext);
        G_whit = whit / 3;
        Alldata = new ArrayList<>();
        imagePaths = new ArrayList<>();
        Intent intent = getIntent();
        try {
            id = intent.getExtras().getString("orgId");
            intent_back = intent.getExtras().getString("back");
            name = intent.getExtras().getString("name");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //获得控件id，初始化id
        drawer_layout_list = (ListView) findViewById(R.id.drawer_layout_list);
        fab = (CircleImageView) findViewById(R.id.fab);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        drawerLayout_smart = (SmartRefreshLayout) findViewById(R.id.drawerLayout_smart);
        drawerLayout_smart.setEnableRefresh(false);
        listerad_nonumber = (LinearLayout) findViewById(R.id.listerad_nonumber);
        search_editext = (EditText) findViewById(R.id.search_editext);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                swip = false;
                s = 1;
                //已处理或未处理
                if (Objects.equals(notall, "false")) {
                    okgo(wbsid, status, null, 1);
                    //已处理或未处理
                } else if (Objects.equals(notall, "true")) {
                    okgo(wbsid, status, null, 1);
                } else if (Objects.equals(notall, "all")) {
                    //全部
                    okgoall(wbsid, null, 1);
                } else if (Objects.equals(notall, "search")) {
                    //搜索
                    search(1);
                }
                //传入false表示刷新失败
                refreshlayout.finishRefresh(2000);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                swip = true;
                if (notall == "false") {
                    s = s + 1;
                    //已处理或未处理
                    okgo(null, status, null, s);
                } else if (notall == "true") {
                    s = s + 1;
                    //已处理或未处理
                    okgo(null, status, null, s);
                } else if (notall == "all") {
                    s = s + 1;
                    //全部
                    okgoall(null, null, s);
                } else if (notall == "search") {
                    s = s + 1;
                    //搜索
                    search(s);
                }
                //传入false表示加载失败
                refreshlayout.finishLoadmore(2000);
            }
        });
        search_editext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(ListreadActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    if (popwind == true) {
                        mPopWindow.dismiss();
                    }
                    swip = false;
                    s = 1;
                    search(1);
                }
                return false;
            }
        });
        search_editext.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    // 触摸移动时的操作
                    if (popwind == true) {
                        mPopWindow.dismiss();
                    }
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });
        uslistView = (ListView) findViewById(R.id.list_recycler);
        Titlew = (TextView) findViewById(R.id.com_title);
        imageView = (LinearLayout) findViewById(R.id.com_img);
        Titlew.setText(name);
        Titlew.setTextSize(17);
        findViewById(R.id.com_back).setOnClickListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popwind == false) {
                    PopupWindow();//打开弹出框
                    popwind = true;
                } else if (popwind == true) {
                    mPopWindow.dismiss();
                    popwind = false;
                }
            }
        });
        mAdapter = new Imageloaders(mContext, Alldata);
        uslistView.setAdapter(mAdapter);
        uslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (Alldata.get(position).getIsFinish() + "") {
                    //回复转发(自己回复或选择转发)
                    //未上传进入详情
                    case "0":
                        Intent intent = new Intent(mContext, AuditparticularsActivity.class);
                        intent.putExtra("frag_id", Alldata.get(position).getTaskId());
                        intent.putExtra("wbsid", Alldata.get(position).getWbsId());
                        intent.putExtra("status", "one");
                        startActivity(intent);
                        break;
                    //通过的详情
                    case "1":
                        Intent audio = new Intent(mContext, AuditparticularsActivity.class);
                        audio.putExtra("frag_id", Alldata.get(position).getTaskId());
                        audio.putExtra("wbsid", Alldata.get(position).getWbsId());
                        audio.putExtra("status", "two");
                        startActivity(audio);
                        break;
                    default:
                        break;
                }
            }
        });
        okgoall(null, null, 1);
        uslistView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 触摸移动时的操作
                        if (popwind == true) {
                            mPopWindow.dismiss();
                        }
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(ListreadActivity.this.getCurrentFocus()
                                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popwind == true) {
                    mPopWindow.dismiss();
                }
                //加载第一页
                page = 1;
                //请求数据时清除之前的
                drew=true;
                //网络请求
                photoAdm(wbsid);
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });
        taskAdapter = new TaskPhotoAdapter(imagePaths, ListreadActivity.this);
        drawer_layout_list.setAdapter(taskAdapter);
        drawer_layout.setScrimColor(Color.TRANSPARENT);
        /**
         *    侧拉listview上拉加载
         */
        drawerLayout_smart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                photoAdm(wbsid);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void search(final int str) {
        String searchContext = search_editext.getText().toString().trim();
        if (TextUtils.isEmpty(searchContext)) {
            Toast.makeText(getApplicationContext(), "输入框为空，请输入搜索内容！", Toast.LENGTH_SHORT).show();
        } else {
            if (status == "3") {
                searchokgo1(wbsid, searchContext, str);
            } else {
                searchokgo(wbsid, status, searchContext, str);
            }
        }
    }

    //弹出框
    void PopupWindow() {
        View contentView = LayoutInflater.from(ListreadActivity.this).inflate(R.layout.popuplayout, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(390);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        contentView.findViewById(R.id.pop_computer).setOnClickListener(this);
        contentView.findViewById(R.id.pop_financial).setOnClickListener(this);
        contentView.findViewById(R.id.pop_manage).setOnClickListener(this);
        contentView.findViewById(R.id.pop_All).setOnClickListener(this);
        contentView.findViewById(R.id.recycler_view).setOnClickListener(this);
        mPopWindow.showAsDropDown(imageView, -10, 0);
        popwind = true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.recycler_view:
                mPopWindow.dismiss();
                popwind = false;
                break;
            //选择wbs
            case R.id.pop_computer:
                Intent intent = new Intent(ListreadActivity.this, MmissPushActivity.class);
                intent.putExtra("data", "List");
                startActivityForResult(intent, 1);
                mPopWindow.dismiss();
                popwind = false;
                break;
            //全部
            case R.id.pop_All:
                Dates.getDialog(ListreadActivity.this, "请求数据中...");
                Alldata.clear();
                search_editext.setText("");
                s = 1;
                status = "3";
                notall = "all";
                okgoall(null, null, 1);
                mPopWindow.dismiss();
                popwind = false;
                break;
            //未处理
            case R.id.pop_financial:
                Dates.getDialog(ListreadActivity.this, "请求数据中...");
                Alldata.clear();
                search_editext.setText("");
                s = 1;
                notall = "false";
                status = "0";
                okgo(null, status, null, 1);
                mPopWindow.dismiss();
                popwind = false;
                break;
            //已处理
            case R.id.pop_manage:
                Dates.getDialog(ListreadActivity.this, "请求数据中...");
                search_editext.setText("");
                s = 1;
                Alldata.clear();
                notall = "true";
                status = "2";
                okgo(null, status, null, 1);
                mPopWindow.dismiss();
                popwind = false;
                break;
            //返回
            case R.id.com_back:
                if (intent_back != null) {
//                  startActivity(new Intent(ListreadActivity.this, MainActivity.class));
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.search_img:
                break;
            default:
                break;
        }
    }

    //组织id查询
    private void okgo(String wbsId, String msgStatus, String content, int i) {
        post(Request.CascadeList)
                .params("orgId", id)
                .params("page", i)
                .params("rows", 10)
                .params("wbsId", wbsId)
                .params("isAll", "true")
                .params("msgStatus", msgStatus)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        parsingjson(s);

                    }
                });
    }

    /**
     * 搜索
     */
    private void searchokgo(String wbsId, String msgStatus, String content, int i) {
        notall = "search";
        OkGo.post(Request.CascadeList)
                .params("orgId", id)
                .params("page", i)
                .params("rows", 10)
                .params("wbsId", wbsId)
                .params("isAll", "true")
                .params("msgStatus", msgStatus)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        parsingjson(s);
                    }
                });
    }
    /**
     * 搜索
     */

    private void searchokgo1(String wbsId, String content, int i) {
        notall = "search";
        OkGo.post(Request.CascadeList)
                .params("orgId", id)
                .params("page", i)
                .params("rows", 10)
                .params("wbsId", wbsId)
                .params("isAll", "true")
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("search", "search1");
                        parsingjson(s);
                    }
                });
    }
    /**
     * 全部
     */

    private void okgoall(String wbsId, String content, int i) {
        post(Request.CascadeList)
                .params("orgId", id)
                .params("page", i)
                .params("rows", 10)
                .params("wbsId", wbsId)
                .params("isAll", "true")
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("search", "search2");
                        parsingjson(s);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是不是Activity的返回，不是就是相机的返回
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            Titlew.setText(title);
            wbsid = data.getStringExtra("id");
            popwind = data.getBooleanExtra("iswbs", false);
            if (popwind != false) {
                fab.setVisibility(View.VISIBLE);
            } else {
                fab.setVisibility(View.GONE);
            }
            page = 1;
            photoAdm(wbsid);
            okgoall(wbsid, null, 1);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            finish();
        }
        return true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        //传入false表示刷新失败
        refreshLayout.finishRefresh(false);
        if (popwind == true) {
            mPopWindow.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private void parsingjson(String s) {

        String wbsPath;
        String updateDate;
        String content;
        String taskId;
        String id;
        String wbsId;
        String createTime;
        String groupName;
        int isFinish;//状态
        if (swip == false) {
            Alldata.clear();
        }
        if (s.indexOf("data") != -1) {
            System.out.println("有数据");
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject json = jsonArray1.getJSONObject(i);
                    JSONObject json1 = new JSONObject();
                    JSONArray json2 = new JSONArray();
                    try {
                        json1 = json.getJSONObject("children");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json2 = json.getJSONArray("comments");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray files = new JSONArray();
                    try {
                        files = json1.getJSONArray("file");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        wbsPath = json.getString("wbsPath");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        wbsPath = "";
                    }
                    try {
                        wbsId = json.getString("wbsId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        wbsId = "";
                    }
                    try {
                        updateDate = json.getString("updateDate");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        updateDate = "";
                    }
                    try {
                        taskId = json.getString("taskId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        taskId = "";
                    }

                    try {
                        isFinish = json.getInt("isFinish");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        isFinish = 0;
                    }
                    try {
                        id = json.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        id = "";
                    }
                    try {
                        groupName = json.getString("pointName");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        groupName = "";
                    }
                    try {
                        createTime = json.getString("createTime");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        createTime = "";
                    }
                    try {
                        content = json.getString("content");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        content = "";
                    }

                    String userId = "", protrait = "", upload_addr = "", upload_content = "", upload_time = "", uploador = "";
//                //个人信息
                    try {
                        userId = json1.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        userId = "";
                    }
                    try {
                        protrait = json1.getString("portrait");
                        protrait = Request.networks + protrait;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        protrait = "";
                    }
                    try {
                        upload_addr = json1.getString("upload_addr");
                    } catch (JSONException e) {
                        upload_addr = "";
                    }
                    try {
                        upload_time = json1.getString("upload_time");
                    } catch (JSONException e) {
                        upload_time = "";
                    }
                    try {
                        uploador = json1.getString("uploador");
                    } catch (JSONException e) {
                        uploador = "";
                    }
                    try {
                        upload_content = json1.getString("upload_content");
                    } catch (JSONException e) {
                        upload_content = "";
                    }
                    paths = new ArrayList<>();
                    if (files.length() > 0) {
                        for (int j = 0; j < files.length(); j++) {
                            paths.add(Request.networks + files.get(j).toString());
                        }
                    }
                    int comments = json2.length();
                    Alldata.add(new Inface_all_item(wbsPath, updateDate, content, taskId, id, wbsId, createTime,
                            groupName, isFinish, upload_time, userId, uploador, upload_content, upload_addr, protrait, paths, comments));
                }
                Dates.disDialog();
                if (Alldata.size() != 0) {
                    mAdapter.getData(Alldata);
                    listerad_nonumber.setVisibility(View.GONE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showShortToast("没有更多数据了！");
            mAdapter.notifyDataSetChanged();
        }
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

}
