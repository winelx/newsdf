package com.example.administrator.newsdf.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by Administrator on 2018/2/5 0005.
 */

// Picasso三方加载库实现的
/** 
 * description:
 * @author: lx
 * date: 2018/2/5 0005 下午 5:24
 * update: 2018/2/5 0005
 * version: 
*/

public class PicassoImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        //配置上下文
        Glide.with(activity)
                //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .load(Uri.fromFile(new File(path)))
                .into(imageView);


    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }
}
