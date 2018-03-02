package com.example.administrator.newsdf.activity.home;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.RectifierAdapter;
import com.example.administrator.newsdf.R;

import java.util.ArrayList;

/**
 * 整改
 */
public class Rectification extends AppCompatActivity {
    private Context mContext;
    private RecyclerView recycler_att, recycler_photo;
    private TextView text_photo, text_att;
    private ArrayList<String> Particulars = new ArrayList<>();
    private ArrayList<String> status = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rectification);
        mContext = getApplicationContext();
        recycler_photo = (RecyclerView) findViewById(R.id.recycler_photo);
        recycler_att = (RecyclerView) findViewById(R.id.recycler_att);
        text_photo = (TextView) findViewById(R.id.text_photo);
        text_att = (TextView) findViewById(R.id.text_att);
        Particulars.add("https://b-ssl.duitang.com/uploads/item/201711/25/20171125233849_uhAeY.thumb.700_0.jpeg");
        Particulars.add("https://b-ssl.duitang.com/uploads/item/201711/25/20171125234047_E3ydP.thumb.700_0.jpeg");
        Particulars.add("https://b-ssl.duitang.com/uploads/item/201711/25/20171125234034_icd5h.thumb.700_0.jpeg");
        Particulars.add("https://b-ssl.duitang.com/uploads/item/201711/25/20171125234019_4T3Mu.thumb.700_0.jpeg");
        Particulars.add("https://b-ssl.duitang.com/uploads/item/201711/25/20171125233849_uhAeY.thumb.700_0.jpeg");
        Particulars.add("https://b-ssl.duitang.com/uploads/item/201711/25/20171125234047_E3ydP.thumb.700_0.jpeg");
        Particulars.add("https://b-ssl.duitang.com/uploads/item/201711/25/20171125234034_icd5h.thumb.700_0.jpeg");
        Particulars.add("https://b-ssl.duitang.com/uploads/item/201711/25/20171125234019_4T3Mu.thumb.700_0.jpeg");
        LinearLayoutManager attManager = new LinearLayoutManager(mContext);
        attManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_att.setLayoutManager(attManager);
        RectifierAdapter adapteratt = new RectifierAdapter(mContext, Particulars);
        recycler_att.setAdapter(adapteratt);

        status.add("https://b-ssl.duitang.com/uploads/item/201707/30/20170730213812_CZXh3.thumb.700_0.jpeg");
        status.add("https://b-ssl.duitang.com/uploads/item/201707/30/20170730213726_CsAhz.thumb.700_0.jpeg");
        status.add("https://b-ssl.duitang.com/uploads/item/201711/25/20171125233912_sYedT.thumb.700_0.jpeg");
        status.add("https://b-ssl.duitang.com/uploads/item/201711/25/20171125233907_AJKQ3.thumb.700_0.jpeg");
        status.add("https://b-ssl.duitang.com/uploads/item/201707/30/20170730213812_CZXh3.thumb.700_0.jpeg");
        status.add("https://b-ssl.duitang.com/uploads/item/201707/30/20170730213726_CsAhz.thumb.700_0.jpeg");
        status.add("https://b-ssl.duitang.com/uploads/item/201711/25/20171125233912_sYedT.thumb.700_0.jpeg");
        status.add("https://b-ssl.duitang.com/uploads/item/201711/25/20171125233907_AJKQ3.thumb.700_0.jpeg");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_photo.setLayoutManager(linearLayoutManager);
        RectifierAdapter adapter = new RectifierAdapter(mContext, status);
        recycler_photo.setAdapter(adapter);

        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
