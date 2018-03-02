package com.example.administrator.newsdf.Bean;

/**
 * 作者：winelx
 * 时间：2017/12/2 0002:上午 11:40
 * 说明：
 */
public class Tab_fragment_item {

    String id;
    String str;//名称
    String content;//内容
    String user;//负责人
    String status1;//状态
    String time;//时间

    public Tab_fragment_item(String id, String str, String content, String user, String status1, String time) {
        this.id = id;
        this.str = str;
        this.content = content;
        this.user = user;
        this.status1 = status1;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
