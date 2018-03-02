package com.example.administrator.newsdf.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class Test {

    /**
     * 传入要写入文件内容的集合
     *
     * @param list
     */
    public Test(List<String> list) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File("c:\\1.txt")));
            for (String str : list) {
// 写文件
                bw.write(str, 0, str.length());
// 刷新流
                bw.flush();
// 换行
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
// 关闭文件流
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("123");
        list.add("asd");
        new Test(list);
    }

    public void getFiles(String path) {
//目标集合fileList
        ArrayList<File> fileList = new ArrayList();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileIndex : files) {
//如果这个文件是目录，则进行递归搜索
                if (fileIndex.isDirectory()) {
                    getFiles(fileIndex.getPath());
                } else {
//如果文件是普通文件，则将文件句柄放入集合中
                    fileList.add(fileIndex);
                }
            }
        }
    }

}
