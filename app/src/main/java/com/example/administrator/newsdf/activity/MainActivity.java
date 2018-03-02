package com.example.administrator.newsdf.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.Bean.Tab;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.fragment.IndexFrament;
import com.example.administrator.newsdf.fragment.MineFragment;
import com.example.administrator.newsdf.fragment.WorkFragment;
import com.example.administrator.newsdf.utils.Dates;

import java.io.File;
import java.util.ArrayList;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;

/**
 * 承载fragemnt的代码
 */
public class MainActivity extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    private static MainActivity mContext;
    private LayoutInflater mInflater;
    private ArrayList<Tab> mTabs = new ArrayList<>();
    private Dates dates;

    public static MainActivity getInstance() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian);

        mContext = MainActivity.this;
        dates = new Dates();
        HiPermission.create(mContext)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });
        dates.deleteAllFiles(new File("/storage/emulated/0/pictures"));
        dates.deleteAllFiles(new File("/storage/emulated/0/Pictures/cropUtils"));
        //数据处理
        initTab();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initTab() {
        //添加tab信息，存入集合进行展示
        Tab tab_home = new Tab(IndexFrament.class, R.string.home, R.drawable.tab_home_style);
        Tab tab_work = new Tab(WorkFragment.class, R.string.work, R.drawable.tab_work_style);
        Tab tab_hot = new Tab(MineFragment.class, R.string.mine, R.drawable.tab_mine_style);
        mTabs.add(tab_home);
        mTabs.add(tab_work);
        mTabs.add(tab_hot);
        //找到控件
        mTabHost = (FragmentTabHost) findViewById(R.id.mFragmentTabHost);
        for (Tab tab : mTabs) {
            //获取都文字
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec, tab.getFragemnt(), null);
        }
        //设置分割线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING);
        //设置默认打开的界面
        mTabHost.setCurrentTab(0);
    }

    private View buildIndicator(Tab tab) {
        mInflater = LayoutInflater.from(this);
        //必须实现setup不然没法展示， getSupportFragmentManager用来装布局的
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        //mInflater记得初始化，不然会报空指针，没注意，被坑
        View view = mInflater.inflate(R.layout.tab_index, null);
        //获取控件ID
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textview = (TextView) view.findViewById(R.id.text);
        //d动态添加图片文字，类似listview 的adapter的getItem，不过本来就是
        imageView.setBackgroundResource(tab.getIcon());
        textview.setText(tab.getTitle());
        return view;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return true;
    }

    private long exitTime = 0;
}
