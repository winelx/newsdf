package com.example.administrator.newsdf.activity.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.newsdf.Adapter.SettingAdapter;
import com.example.administrator.newsdf.Bean.Makeup;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class ChoosecheckActivity extends AppCompatActivity {
    private ListView list_item;
    private ArrayList<String> list;
    private ArrayList<Makeup> mData;
    private SettingAdapter mAdapter;
    private Context mContent;
    private SPUtils spUtils;
    private ImageView backgroud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosecheck);
        mContent = ChoosecheckActivity.this;
        list = new ArrayList<>();
        mData = new ArrayList<>();
        okGo();
        spUtils = new SPUtils();
        Intent intent = getIntent();
        list = intent.getStringArrayListExtra("list");
        list_item = (ListView) findViewById(R.id.list_item);
        backgroud = (ImageView) findViewById(R.id.backgroud);
        mAdapter = new SettingAdapter<Makeup>(mData, R.layout.choose_item) {
            @Override
            public void bindView(ViewHolder holder, final Makeup obj) {
                holder.setText(R.id.check, obj.getName());
                holder.setOnClickListener(R.id.check, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //返回列表
                        Intent intent = new Intent();
                        intent.putExtra("id", obj.getId());//内容
                        intent.putExtra("name", obj.getName());//内容
                        setResult(2, intent);//回传数据到主Activity
                        finish(); //此方法后才能返回主Activity
                    }
                });
            }
        };
        list_item.setAdapter(mAdapter);

    }

    private void okGo() {
        OkGo.post(Request.Members)
                .params("orgId", spUtils.getString(mContent, "orgId", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("res", s);
                        list.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String str = jsonObject.getString("msg");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject content = jsonArray.getJSONObject(i);
                                String id = content.getString("id");
                                String userId = content.getString("userId");
                                String name = content.getString("name");
                                String moblie = content.getString("moblie");
                                String imageUrl = content.getString("imageUrl");
                                mData.add(new Makeup(name, userId));
                            }
                            if (mData.size() != 0) {
                                backgroud.setVisibility(View.GONE);
                            }
                            mAdapter.getData(mData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
