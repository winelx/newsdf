package com.example.administrator.newsdf.Bean;

/**
 * 作者：winelx
 * 时间：2017/11/22 0022:下午 17:29
 * 说明：
 */
public class Icon {
    String id;   //用户ID
    String userId; //职员ID
    String name; //姓名
    String moblie; //手机号
    String imageUrl; //头像

    public Icon(String id, String userId, String name, String moblie, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.moblie = moblie;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
