package com.example.administrator.newsdf.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.administrator.newsdf.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 作者：winelx
 * 时间：2017/11/27 0027:下午 14:59
 * 说明：
 */
public class Dates {
    public static Dialog progressDialog;

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    //时间戳转时间
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        res = res.substring(0, res.length() - 3);
        return res;
    }

    public static String datato(String str) throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date begin = dfs.parse(str);
        java.util.Date end = dfs.parse(getDate());
        Log.i("begin", begin + "" + end + "");
        //除以1000是为了转换成秒
        long between = (end.getTime() - begin.getTime()) / 1000;
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60 / 60;
//        System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒");
        return  day1 + "天" + hour1 + "小时";
    }

    //获取时间
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public static String getHH() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    //加载图片
    public static void getImg(Context context, String imageUrl, ImageView view) {
        Glide.with(context)
                .load(imageUrl)
                .transition(new DrawableTransitionOptions().crossFade(2000))
                .thumbnail(Glide.with(context)
                        .load(R.mipmap.mine_avatar))
                .into(view);
    }

    public static void setback(Context context, String imageUrl, ImageView view) {
        Glide.with(context).load(imageUrl).into((ImageView) view);
    }

    //判断权限是否开启
    private boolean checkWriteExternalPermission(Context context) {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE"; //你要判断的权限名字
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    //删除保存本地图片
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                // 判断是否为文件夹
                if (f.isDirectory()) {
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    // 判断是否存在
                    if (f.exists()) {
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    //删除文件和目录
    public void clearFiles(String workspaceRootPath) {
        File file = new File(workspaceRootPath);
        if (file.exists()) {
            deleteFile(file);
        }
    }

    private void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
        file.delete();
    }


    //保存添加水印的 图片
    public static String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        // Environment.getExternalStorageDirectory()==/storage/emulated/0/   Boohee：为指定的文件夹
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //设置系统时间为文件名
        String fileName = System.currentTimeMillis() + ".jpg";
        Log.i("Boohee", fileName);
        //文件夹和文件名
        File file = new File(appDir, fileName);
        try {
            //使用输入流将数据写入本地，
            FileOutputStream fos = new FileOutputStream(file);
            //设置保存的 文件格式，是否压缩，输入流
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush(); //刷新文件流
            fos.close();//关闭输入流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 其次把文件插入到系统图库
         * 注：这段代码会保存两张图片；
         *
         */
//
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        // 返回路径展示图片
        return appDir + "/" + fileName;

    }

    public static Bitmap getBitmap(String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        //此时返回bm为空
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是1920f*1080f分辨率，所以高和宽我们设置为
        //这里设置高度为1920f
        float hh = 1920f;
        //这里设置宽度为1080f
        float ww = 1080f;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        //be=1表示不缩放
        int be = 1;
        //如果宽度大的话根据宽度固定大小缩放
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
            //如果高度高的话根据宽度固定大小缩放
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //设置缩放比例
        newOpts.inSampleSize = be;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        //压缩好比例大小后再进行质量压缩
        return compressImage(bitmap);

    }

    public static Bitmap compressPixel(String filePath) {
        Bitmap bmp = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = 2;
        //inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[16 * 1024];
        try {
            //load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
            if (bmp == null) {

                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(filePath);
                    BitmapFactory.decodeStream(inputStream, null, options);
                    inputStream.close();
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        } finally {
            return bmp;
        }
    }

    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Log.i("压缩", "yas");
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        int options = 100;
        //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 250) {
            //重置baos即清空baos
            baos.reset();
            //每次都减少10
            options -= 15;
            //这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 获取应用的 SHA1 值， 可据此判断高德，百度地图 key 是否正确
     *
     * @param context 上下文
     * @return 应用的 SHA1 字符串, 比如： 53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
     */

    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (byte aPublicKey : publicKey) {
                String appendString = Integer.toHexString(0xFF & aPublicKey).toUpperCase();
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    //集合转string
    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

    //string转集合
    public static List<String> stringToList(String strs) {
        if (strs == "" && strs.isEmpty()) {

        } else {
            String str[] = strs.split(",");
            return Arrays.asList(str);
        }
        return null;
    }

    /**
     * 判断当前软键盘是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputShow(Activity activity) {

        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
//       inputmanger.hideSoftInputFromWindow(view.getWindowToken(),0);

            return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }

    public static void getDialog(Activity activity, String str) {
        progressDialog = new Dialog(activity, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.waiting_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView text = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        text.setText(str);
        progressDialog.show();
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                progressDialog.dismiss();
                return false;
            }
        }).sendEmptyMessageDelayed(0, 2000);//表示延迟3秒发送任务

    }

    public static void getDialogs(Activity activity, String str) {
        progressDialog = new Dialog(activity, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.waiting_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView text = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        text.setText(str);
        progressDialog.show();
    }

    public static void disDialog() {
        progressDialog.dismiss();
    }
}