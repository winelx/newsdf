package com.example.administrator.newsdf.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.SettingAdapter;
import com.example.administrator.newsdf.Adapter.Fr_work_pie;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.View.IPieElement;
import com.example.administrator.newsdf.View.PieChartBeans;
import com.example.administrator.newsdf.View.PieChartOne;
import com.example.administrator.newsdf.activity.work.OrganiwbsActivity;
import com.example.administrator.newsdf.activity.work.PchooseActivity;
import com.example.administrator.newsdf.activity.work.PushCheckActivity;
import com.example.administrator.newsdf.activity.work.NotuploadActivity;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * @author ：lx
 *         时间：2017/11/23 0023:下午 15:37
 *         说明：
 */
public class WorkFragment extends Fragment {
    private View rootView;
    private Context mContext;
    private LinearLayout one, push, pphotoadm, uploade;
    private List<IPieElement> list;
    private TextView fr_work_dn, fr_work_name;
    private PieChartOne PieChartOne;
    private List<PieChartBeans> mData;
    private GridView fr_work_grid;
    private SettingAdapter mAdapter;
    String name = "";
    int num = 0;
    String[] color = {"#2F4554", "#D48265", "#91C7AE", "#749F83", "#C23531", "#61A0A8", "#61a882", "#68a861", "#618ca8"};
    int number = 0;
    private ArrayList<Fr_work_pie> workpie = new ArrayList<>();
    boolean status = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//避免重复绘制界面
        if (rootView == null) {
            mContext = getActivity();
            rootView = inflater.inflate(R.layout.fragment_work, null);
            mData = new ArrayList<>();
            list = new ArrayList<>();
            one = rootView.findViewById(R.id.one);
            fr_work_grid = rootView.findViewById(R.id.fr_work_grid);
            PieChartOne = rootView.findViewById(R.id.piechartone);
            fr_work_dn = rootView.findViewById(R.id.fr_work_dn);
            fr_work_name = rootView.findViewById(R.id.fr_work_name);
            fr_work_name.setText(SPUtils.getString(mContext, "staffName", null) + ",");

            String data = Dates.getHH();
            int time = Integer.parseInt(data);
            if (time >= 6 && time < 12) {
                fr_work_dn.setText("上午好 !");
            } else if (time >= 12 && time < 14) {
                fr_work_dn.setText("中午好 !");
            } else if (time >= 14 && time < 19) {
                fr_work_dn.setText("下午好 !");
            } else if (time >= 19 && time <= 23) {
                fr_work_dn.setText("晚上好 !");
            } else {
                fr_work_dn.setText("早上好 !");
            }

            push = (LinearLayout) rootView.findViewById(R.id.push);
            pphotoadm = (LinearLayout) rootView.findViewById(R.id.pphotoadm);
            uploade = rootView.findViewById(R.id.uploade);
            one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OrganiwbsActivity.class);
                    startActivity(intent);
                }
            });
            push.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PushCheckActivity.class);
                    startActivity(intent);

                }
            });
            pphotoadm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, PchooseActivity.class));

                }
            });
            uploade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NotuploadActivity.class);
                    startActivity(intent);
                }
            });
            mAdapter = new SettingAdapter<PieChartBeans>(mData, R.layout.fr_work_gr_item) {
                @Override
                public void bindView(ViewHolder holder, PieChartBeans obj) {
                    holder.setBackgroud(R.id.fr_worl_gr_img, obj.getColor());
                    holder.setText(R.id.fr_worl_gr_text, obj.getValuer());
                }
            };
        }
        PieChartOne.setDate(mData);
        fr_work_grid.setAdapter(mAdapter);
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (status != false) {
            okgo1();
        } else {
            okgo();
            PieChartOne.setDate(mData);
            status = true;
        }
    }
    //走oncreate
    private void okgo() {
        mData.clear();
        workpie.clear();
        OkGo.post(Request.PieChart)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            num = 0;
                            number = 0;
                            JSONObject jsonObject = new JSONObject(s);
                            if (s.indexOf("data") != -1) {
                                System.out.println("包含");
                                JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject json = jsonArray1.getJSONObject(i);
                                    num = json.getInt("num");
                                    Log.i("ss", num + "");
                                    number = number + num;
                                    Log.i("ss", number + "");
                                    try {
                                        name = json.getString("name");
                                    } catch (JSONException e) {
                                        name = "";
                                    }
                                    workpie.add(new Fr_work_pie(name, num, color[i]));
                                }
                                Float numbers = Float.valueOf(number);
                                for (int i = 0; i < workpie.size(); i++) {
                                    Float siz = Float.valueOf(workpie.get(i).getNum());
                                    Float sjie = siz / numbers * 100;
                                    BigDecimal b = new BigDecimal(sjie);
                                    float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                                    mData.add(new PieChartBeans(workpie.get(i).getName(), f1, workpie.get(i).getColor()));
                                }
                                mAdapter.getData(mData);
                            } else {
                                System.out.println("不包含");
                                mData.add(new PieChartBeans("未开始开工", 100f, color[1]));
                                mAdapter.getData(mData);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });
    }

    //走onstart
    private void okgo1() {
        mData.clear();
        workpie.clear();
        OkGo.post(Request.PieChart)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            num = 0;
                            number = 0;
                            JSONObject jsonObject = new JSONObject(s);
                            if (s.indexOf("data") != -1) {
                                System.out.println("包含");
                                JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject json = jsonArray1.getJSONObject(i);

                                    try {
                                        num = json.getInt("num");
                                        number = number + num;
                                    } catch (JSONException e) {
                                        number = 0;
                                    }

                                    try {
                                        name = json.getString("name");
                                    } catch (JSONException e) {
                                        name = "";
                                    }
                                    workpie.add(new Fr_work_pie(name, num, color[i]));
                                }
                                Float numbers = Float.valueOf(number);
                                for (int i = 0; i < workpie.size(); i++) {
                                    Float siz = Float.valueOf(workpie.get(i).getNum());
                                    Float sjie = siz / numbers * 100;
                                    BigDecimal b = new BigDecimal(sjie);
                                    float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                                    mData.add(new PieChartBeans(workpie.get(i).getName(), f1, workpie.get(i).getColor()));
                                }
                                mAdapter.getData(mData);
                            } else {
                                System.out.println("不包含");
                                mData.add(new PieChartBeans("未开始开工", 100f, color[1]));
                                mAdapter.getData(mData);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        PieChartOne.requestLayout();
                        PieChartOne.setDate(mData);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
}