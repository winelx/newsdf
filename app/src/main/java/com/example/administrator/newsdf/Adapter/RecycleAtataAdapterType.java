package com.example.administrator.newsdf.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.AuditparticularsActivity;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;


/**
 * description:
 * @author lx
 * date:2017/12/13 0013.
 * update: 2018/2/6 0006
 * version:
*/

public class RecycleAtataAdapterType extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Dialog mCameraDialog;
    private Context mContext;
    private ArrayList<Aduio_data> mDatas;
    AuditparticularsActivity activity;
    String str = null;

    public RecycleAtataAdapterType(Context mContext) {
        this.mContext = mContext;
        mDatas = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //主体内容
        return new Viewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.audio_data_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindContent((Viewholder) holder, position);
    }

    //内容主题
    private void bindContent(Viewholder holder, final int posotion) {
        if(mDatas.get(posotion).getAttachments().size()==0){
            holder.audio_rec.setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(mDatas.get(posotion).getUserpath())
                .into(holder.audio_acathor);
        //上传人
        holder.audio_name.setText(mDatas.get(posotion).getReplyUserName());
        //附加内容
        holder.audio_content.setText(mDatas.get(posotion).getUploadContent());
        //上传时间
        holder.audio_data.setText(mDatas.get(posotion).getUpdateDate());
        //上传地点
        holder.audio_address.setText(mDatas.get(posotion).getUploadAddr());
        //评论条数
        holder.comment_count.setText(mDatas.get(posotion).getCommentCount());
        //评论
        holder.audio_data_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.audio_rec.setLayoutManager(linearLayoutManager);
        RectifierAdapter adapter = new RectifierAdapter(mContext, mDatas.get(posotion).getAttachments());
        holder.audio_rec.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class Viewholder extends RecyclerView.ViewHolder {
        private CircleImageView audio_acathor;
        private TextView audio_name, audio_content, audio_data, audio_address, comment_count;
        private RecyclerView audio_rec;
        private LinearLayout audio_data_comm;

        public Viewholder(View itemView) {
            super(itemView);
            //头像
            audio_acathor = (CircleImageView) itemView.findViewById(R.id.audio_acathor);
            //文字内容
            audio_name = (TextView) itemView.findViewById(R.id.audio_name);
            audio_content = (TextView) itemView.findViewById(R.id.audio_content);
            audio_data = (TextView) itemView.findViewById(R.id.audio_data);
            audio_address = (TextView) itemView.findViewById(R.id.audio_address);
            //图片
            audio_rec = (RecyclerView) itemView.findViewById(R.id.audio_rec);
            //评论
            audio_data_comm = (LinearLayout) itemView.findViewById(R.id.audio_data_comm);
            comment_count = (TextView) itemView.findViewById(R.id.audio_data_commom_count);
        }
    }

    public void getdata(ArrayList<Aduio_data> mDatas) {
        this.mDatas = mDatas;
    }

    //弹出框。
    private void setDialog() {
        mCameraDialog = new Dialog(mContext, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.dialog_custom, null);
        //初始化视图
        final Button send = (Button) root.findViewById(R.id.par_button);
        final EditText editext = (EditText) root.findViewById(R.id.par_editext);
        //拿到回复人
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity = (AuditparticularsActivity) mContext;
                String ID = activity.getId();
                str = editext.getText().toString();
                if (str == null || str.isEmpty()) {
                    ToastUtils.showShortToast("回复不能为空");
                } else {
                    OkGo.post(Request.commentaries)
                            .params("taskId", ID)
                            .params("status", "4")
                            .params("content", str)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    activity.getpinL(str);
                                    mCameraDialog.dismiss();
                                }
                            });
                }


            }
        });
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        // 添加动画
        dialogWindow.setWindowAnimations(R.style.DialogAnimation);
        // 获取对话框当前的参数值
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        // 新位置X坐标
        lp.x = 0;
        // 新位置Y坐标
        lp.y = 0;
        // 宽度
        lp.width = (int) mContext.getResources().getDisplayMetrics().widthPixels;
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        // 透明度
        lp.alpha = 9f;
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }
}
