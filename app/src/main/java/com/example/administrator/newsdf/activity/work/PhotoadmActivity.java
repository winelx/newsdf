package com.example.administrator.newsdf.activity.work;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.PhotoadmAdapter;
import com.example.administrator.newsdf.Bean.PhotoBean;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.DividerItemDecoration;
import com.example.administrator.newsdf.utils.Request;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


/**
 * description:
 * @author lx
 * date: 2017/12/13 0013.
 * version:
*/
public class PhotoadmActivity extends AppCompatActivity {
    private RecyclerView photo_rec;
    private PhotoadmAdapter photoAdapter;
    private ArrayList<PhotoBean> imagePaths;
    private TextView number, com_title, wbsname;
    private IconTextView comback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoadm);
        Dates.getDialog(PhotoadmActivity.this, "请求数据中...");
        imagePaths = new ArrayList<>();
        Intent intent = getIntent();
        final String wbsid = intent.getExtras().getString("wbsId");
        final String Title = intent.getExtras().getString("title");
        photo_rec = (RecyclerView) findViewById(R.id.photo_rec);
        wbsname = (TextView) findViewById(R.id.wbsname);
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("图纸详情");
        comback = (IconTextView) findViewById(R.id.com_back);
        comback.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        //GridLayout 3列
        photo_rec.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        photoAdapter = new PhotoadmAdapter(this);
        photo_rec.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        photo_rec.setAdapter(photoAdapter);
        OkGo.post(Request.Photolist)
                .params("WbsId", wbsid)
                .params("page", 1)
                .params("rows", 50)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("photo", s);
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
                            photoAdapter.getData(imagePaths);
                            wbsname.setText(Title + ":" + "共有" + imagePaths.size() + "张图纸");
                            Dates.disDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
