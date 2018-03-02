package com.example.administrator.newsdf.Bean;

/**
 * 作者：winelx
 * 时间：2017/11/28 0028:下午 16:11
 * 说明：
 */
public class Home_item {

    String content;
    String creaeTime;
    String id;
    String orgid;
    String orgname;
    String unfinish;

    public Home_item(String content, String creaeTime, String id, String orgid, String orgname, String unfinish) {
        this.content = content;
        this.creaeTime = creaeTime;
        this.id = id;
        this.orgid = orgid;
        this.orgname = orgname;
        this.unfinish = unfinish;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreaeTime() {
        return creaeTime;
    }

    public void setCreaeTime(String creaeTime) {
        this.creaeTime = creaeTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getUnfinish() {
        return unfinish;
    }

    public void setUnfinish(String unfinish) {
        this.unfinish = unfinish;
    }
}
