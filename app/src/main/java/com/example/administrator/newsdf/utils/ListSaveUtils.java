package com.example.administrator.newsdf.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.newsdf.baseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class ListSaveUtils {
    /**
     * 数据存放在本地
     *
     * @param tArrayList
     */
    public static void saveStorage2SDCard(Context context, List tArrayList, String fileName) {

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            String path = String.valueOf(Environment.getExternalStorageDirectory());
            Log.i("path", path);
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            fileOutputStream = new FileOutputStream(file.toString());  //新建一个内容为空的文件
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (objectOutputStream != null) {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
    }


    /**
     * 获取本地的List数据
     *
     * @return
     */
    public static ArrayList<String> getStorageEntities(String fileName) {
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;
        ArrayList<String> savedArrayList = new ArrayList<>();
        try {

            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            fileInputStream = new FileInputStream(file.toString());
            objectInputStream = new ObjectInputStream(fileInputStream);
            savedArrayList = (ArrayList<String>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return savedArrayList;
    }

    /**
     * 获取本地的List数据
     *
     * @return
     */
    public static ArrayList<Integer> getStorageEntitiesSpinner(String fileName) {
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;
        ArrayList<Integer> savedArrayList = new ArrayList<>();
        try {
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            fileInputStream = new FileInputStream(file.toString());
            objectInputStream = new ObjectInputStream(fileInputStream);
            savedArrayList = (ArrayList<Integer>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return savedArrayList;
    }
}