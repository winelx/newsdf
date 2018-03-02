package com.example.administrator.newsdf.Bean;

import java.util.ArrayList;

/**
 * 作者：winelx
 * 时间：2017/12/1 0001:下午 15:59
 * 说明：评论的内容实体
 */
public class AudioBean {
    String user; //评论人名字
    String content;//评论内容
    String img;//头像
    String data;//时间
    String address;//定位地址
    String com_number;//评论数
    boolean Backto;//是否 打回
    boolean Getthrough;//是否通过
    ArrayList<String> imgurl;//评论图片
    ArrayList<Audio> audioBeen;//评论内容

    public AudioBean(String user, String content, String img, String data, String address, String com_number, boolean backto, boolean getthrough, ArrayList<String> imgurl, ArrayList<Audio> audioBeen) {
        this.user = user;
        this.content = content;
        this.img = img;
        this.data = data;
        this.address = address;
        this.com_number = com_number;
        Backto = backto;
        Getthrough = getthrough;
        this.imgurl = imgurl;
        this.audioBeen = audioBeen;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCom_number() {
        return com_number;
    }

    public void setCom_number(String com_number) {
        this.com_number = com_number;
    }

    public boolean isBackto() {
        return Backto;
    }

    public void setBackto(boolean backto) {
        Backto = backto;
    }

    public boolean isGetthrough() {
        return Getthrough;
    }

    public void setGetthrough(boolean getthrough) {
        Getthrough = getthrough;
    }

    public ArrayList<String> getImgurl() {
        return imgurl;
    }

    public void setImgurl(ArrayList<String> imgurl) {
        this.imgurl = imgurl;
    }

    public ArrayList<Audio> getAudioBeen() {
        return audioBeen;
    }

    public void setAudioBeen(ArrayList<Audio> audioBeen) {
        this.audioBeen = audioBeen;
    }
}
