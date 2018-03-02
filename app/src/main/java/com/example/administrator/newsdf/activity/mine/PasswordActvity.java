package com.example.administrator.newsdf.activity.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description: 修改密码
 * @author: lx
 * date: 2018/2/6 0006 上午 9:43
 * update: 2018/2/6 0006
 * version:
*/
public class PasswordActvity extends AppCompatActivity implements View.OnClickListener {
    private Button commint;
    private AppCompatEditText pass_old, pass_new, pass_newtoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_actvity);
        pass_old = (AppCompatEditText) findViewById(R.id.password_old);
        pass_new = (AppCompatEditText) findViewById(R.id.password_new);
        pass_newtoo = (AppCompatEditText) findViewById(R.id.password_newtoo);
        findViewById(R.id.password_commint).setOnClickListener(this);
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        String old = pass_old.getText().toString();
        String news = pass_new.getText().toString();
        String newtoo = pass_newtoo.getText().toString();
        switch (v.getId()) {
            //修改密码
            case R.id.password_commint:
                if (TextUtils.isEmpty(news) && TextUtils.isEmpty(old) && TextUtils.isEmpty(newtoo)) {
                    Toast.makeText(this, "还有未填项", Toast.LENGTH_SHORT).show();
                } else {
                    if (news.equals(newtoo)) {
                        okgo(old, newtoo);
                    } else {
                        Toast.makeText(this, "不相等", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            default:
                break;
        }
    }

    /**
     *     网络请求
     */
    void okgo(String old, String news) {
        OkGo.post(Request.AlterPwd)
                .params("oldPwd", old)
                .params("newPwd", news)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Toast.makeText(PasswordActvity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PasswordActvity.this, "修改密码失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                });
    }
}
