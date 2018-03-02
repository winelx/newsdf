package com.example.administrator.newsdf.Bean;


import java.util.ArrayList;

/**
 * Created by 10942 on 2017/12/10 0010.
 */

/**
 * 评论对象
 */
public class Moment {
    public String mContent;
    public ArrayList<Comment> mComment; // 评论列表

    public Moment(String mContent, ArrayList<Comment> mComment) {
        this.mComment = mComment;
        this.mContent = mContent;
    }
}
